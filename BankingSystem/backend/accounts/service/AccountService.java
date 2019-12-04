package accounts.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import accounts.Account;
import accounts.AccountStatus;
import accounts.AccountUtility;
import dao.interfaces.AccountDAO;
import dao.interfaces.TransactionDAO;
import transactions.Transaction;
import transactions.TransactionType;

/**
 * Service layer for Account to interact with the Account DAO.
 * 
 * @author ripke1tj
 *
 */
public class AccountService {

	private AccountDAO accountDAO;
	private TransactionDAO transactionDAO;

	public AccountService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
		this.accountDAO = accountDAO;
		this.transactionDAO = transactionDAO;
	}

	/**
	 * Adds an account to the database.
	 * 
	 * @param account: The account to add.
	 * @return: True if added successfully, false otherwise.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public boolean addAccount(Account account) throws FileNotFoundException {
		return this.accountDAO.addAccount(account);
	}

	/**
	 * Removes a bank account from the system if they have withdrawn all monies.
	 * 
	 * @param accountId: The id of the account.
	 * @return: True if removed successfully, false otherwise.
	 * @throws IOException:    Throw if there was an error reading or writing to the
	 *                         database.
	 * @throws ParseException: Thrown if there is an issue parsing the birth date of
	 *                         the user.
	 */
	public boolean removeAccount(String accountId) throws IOException, ParseException {
		Account account = this.accountDAO.getAccount(accountId);
		if (account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalStateException(
					"To close an account, the account must not be overdrawn: " + account.getAccountBalance());
		}
		return this.accountDAO.removeAccount(accountId);
	}

	/**
	 * Writes the deposit transaction to the transaction database.
	 * 
	 * @param account: The account to withdraw from.
	 * @param amount:  The amount to withdraw.
	 * @return: True if successful
	 * @throws IOException: Throw if there was an error reading or writing to the
	 *                      database.
	 */
	public boolean deposit(Account account, String amount) throws IOException {
		Transaction transaction = this.depositTransaction(account, amount);
		accountDAO.updateAccount(account);
		transactionDAO.writeTransaction(transaction);
		return true;
	}

	/**
	 * Used to deposit a valid amount into an account.
	 * 
	 * @param amount: The amount to deposit.
	 * @return: A transaction.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	private Transaction depositTransaction(Account account, String amount) throws FileNotFoundException {
		BigDecimal validAmount = AccountUtility.getValidAmount(amount);
		BigDecimal newBalance = account.getAccountBalance().add(AccountUtility.getValidAmount(amount));
		account.setAccountBalance(newBalance);
		if (account.getAccountStatus() == AccountStatus.OVERDRAWN && newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			account.setAccountStatus(AccountStatus.ACTIVE);
		}
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.DEPOSIT, validAmount,
				account.getAccountBalance());
	}

	/**
	 * Writes the withdraw transaction to the transaction database.
	 * 
	 * @param account: The account to withdraw from.
	 * @param amount:  The amount to withdraw.
	 * @return: True if successful
	 * @throws IOException: Throw if there was an error reading or writing to the
	 *                      database.
	 */
	public boolean withdraw(Account account, String amount) throws IOException {
		Transaction transaction = this.withdrawTransactions(account, amount);
		accountDAO.updateAccount(account);
		transactionDAO.writeTransaction(transaction);
		if (account.isAbleToOverDraft() && account.getAccountBalance().compareTo(BigDecimal.ZERO) < 0) {
			transaction = OverDraftFeeService.applyOverDraftFee(account);
			accountDAO.updateAccount(account);
			transactionDAO.writeTransaction(transaction);
		}
		return true;
	}

	/**
	 * Used to withdraw a valid amount into an account.
	 * 
	 * @param amount: The amount to withdraw.
	 * @return: A transaction.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	private Transaction withdrawTransactions(Account account, String amount) throws FileNotFoundException {
		BigDecimal validAmount = AccountUtility.getValidAmount(amount);
		BigDecimal newBalance = account.getAccountBalance().subtract(AccountUtility.getValidAmount(amount));
		if ((newBalance.compareTo(BigDecimal.ZERO) < 0 && !account.isAbleToOverDraft())) {
			throw new IllegalStateException("Account is not allowed to overdraft.");
		} else if (account.isAbleToOverDraft()
				&& newBalance.compareTo(OverDraftFeeService.getOverDraftFeeLimit(account.getAccountType())) <= 0) {
			throw new IllegalStateException("Withdraw exceeds over draft maximum of "
					+ OverDraftFeeService.getOverDraftFeeLimit(account.getAccountType()).toPlainString());
		} else {
			account.setAccountBalance(newBalance);
			return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.WITHDRAW, validAmount,
					account.getAccountBalance());
		}
	}

}

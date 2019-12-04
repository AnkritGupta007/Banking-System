package accounts.service;

import java.io.FileNotFoundException;

/*
 * Written by Taylor Ripke
 * ripke1tj@cmich.edu
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import accounts.Account;
import dao.interfaces.AccountDAO;
import dao.interfaces.TransactionDAO;
import transactions.Transaction;
import transactions.TransactionType;

/**
 * Used to apply monthly fees to an account on the last day of the month.
 * 
 * @author ripke1tj
 *
 */
public final class MonthlyFeeService {

	private TransactionDAO transactionDAO;
	private AccountDAO accountDAO;

	public MonthlyFeeService(TransactionDAO transactionDAO, AccountDAO accountDAO) {
		this.transactionDAO = transactionDAO;
		this.accountDAO = accountDAO;
	}

	/**
	 * Applies a monthly fee to all accounts on the last day of the month.
	 * 
	 * @throws IOException:    Thrown if a connection could not be established with
	 *                         the database.
	 * @throws ParseException: Thrown if there is an issue parsing the birth date of
	 *                         the user.
	 */
	public void applyMonthlyFee() throws IOException, ParseException {
		List<Account> accounts = accountDAO.getAllAccounts();
		for (Account account : accounts) {
			BigDecimal fee = this.getAccountMonthlyFee(account);
			account.setAccountBalance(account.getAccountBalance().subtract(fee));
			accountDAO.updateAccount(account);
			transactionDAO.writeTransaction(new Transaction(account.getAccountId(), LocalDate.now(),
					TransactionType.MONTHLY_FEE, fee, account.getAccountBalance()));
		}
	}

	/**
	 * For each account, calculates the monthly fee.
	 * 
	 * @param account: The account to calculate the fee for.
	 * @return: The fee to be applied to the account.
	 * @throws FileNotFoundException: Thrown if a connection could not be
	 *                                established with the database.
	 * @throws ParseException         : Thrown if there is an issue parsing the
	 *                                birth date of the user.
	 */
	private BigDecimal getAccountMonthlyFee(Account account) throws FileNotFoundException, ParseException {
		BigDecimal fee = BigDecimal.ZERO;
		switch (account.getAccountType()) {
		case PERSONAL_CHECKING:
			fee = new BigDecimal("10.00");
			LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			List<Transaction> transactions = transactionDAO.getTransactions(account.getAccountId(), firstDayOfMonth,
					lastDayOfMonth);
			boolean feeWaived = false;
			boolean dailyBalanceAbove500 = true;
			for (Transaction transaction : transactions) {
				if (transaction.getTransactionType() == TransactionType.DEPOSIT
						&& transaction.getAmount().compareTo(new BigDecimal("500.00")) >= 0) {
					feeWaived = true;
					break;
				} else if (transaction.getAccountBalance().compareTo(new BigDecimal("1500.00")) < 0) {
					dailyBalanceAbove500 = false;
					break;
				}
			}
			List<Account> accounts = accountDAO.getUserAccounts(account.getDriversLicense());
			BigDecimal average = BigDecimal.ZERO;
			BigDecimal sum = BigDecimal.ZERO;
			for (Account a : accounts) {
				sum = sum.add(a.getAccountBalance());
			}
			if (average.compareTo(BigDecimal.ZERO) > 0) {
				average = sum.divide(BigDecimal.valueOf(accounts.size()), 15, RoundingMode.HALF_UP);
			}
			if (average.compareTo(new BigDecimal("5000.00")) >= 0) {
				feeWaived = true;
			}

			if (feeWaived == true || dailyBalanceAbove500 == true) {
				fee = BigDecimal.ZERO;
			}
			break;
		case BUSINESS_CHECKING:
			fee = new BigDecimal("25.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("2500.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case STUDENT_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("150.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case PERSONAL_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("300.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		case BUSINESS_SAVINGS:
			fee = new BigDecimal("5.00");
			if (account.getAccountBalance().compareTo(new BigDecimal("3500.00")) >= 0) {
				fee = BigDecimal.ZERO;
			}
			break;
		default:
			break;
		}
		return fee;
	}

}

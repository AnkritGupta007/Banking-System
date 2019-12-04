package accounts.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import accounts.Account;
import dao.interfaces.AccountDAO;
import dao.interfaces.TransactionDAO;
import transactions.Transaction;
import transactions.TransactionType;

/**
 * Used to add interest to applicable accounts.
 * 
 * @author ripke1tj
 *
 */
public final class InterestGainingService {

	/*
	 * Represents the compound interest rate used for daily compounds.
	 */
	private static final BigDecimal COMPOUND_INTEREST_RATE = new BigDecimal("0.01");
	/*
	 * Represents the number of compounds per year.
	 */
	private static final BigDecimal COMPOUNDINGS_PER_YEAR = new BigDecimal("365");
	/*
	 * Represents how often a period lasts.
	 */
	private static final int COMPOUNDING_PERIOD = 1;

	private TransactionDAO transactionDAO;
	private AccountDAO accountDAO;

	public InterestGainingService(TransactionDAO transactionDAO, AccountDAO accountDAO) {
		this.transactionDAO = transactionDAO;
		this.accountDAO = accountDAO;
	}

	/**
	 * Applies compound interest to all applicable accounts.
	 * 
	 * @throws ParseException: Thrown if there is an issue parsing the birth date of
	 *                         the user.
	 * @throws IOException:    Thrown if a connection could not be established with
	 *                         the database.
	 */
	public void applyCompoundInterest() throws ParseException, IOException {
		List<Account> accounts = accountDAO.getAllAccounts();
		for (Account account : accounts) {
			BigDecimal compoundInterest = this.getCompoundInterest(account);
			if (compoundInterest.compareTo(BigDecimal.ZERO) > 0) {
				account.setAccountBalance(account.getAccountBalance().add(compoundInterest));
				accountDAO.updateAccount(account);
				transactionDAO.writeTransaction(new Transaction(account.getAccountId(), LocalDate.now(),
						TransactionType.INTEREST, compoundInterest, account.getAccountBalance()));
			}

		}
	}

	/**
	 * Computes the money earned per day from daily compound interest.
	 * 
	 * @return The daily compound interest earnings.
	 */
	private BigDecimal getCompoundInterest(Account account) {
		BigDecimal compoundInterest = BigDecimal.ZERO;
		switch (account.getAccountType()) {
		case STUDENT_SAVINGS:
		case PERSONAL_SAVINGS:
		case BUSINESS_SAVINGS:
			compoundInterest = account.getAccountBalance()
					.multiply((BigDecimal.ONE
							.add(COMPOUND_INTEREST_RATE.divide(COMPOUNDINGS_PER_YEAR, 15, RoundingMode.HALF_UP))
							.pow(COMPOUNDING_PERIOD).subtract(BigDecimal.ONE)));
			break;
		default:
			break;
		}
		return compoundInterest;
	}
}

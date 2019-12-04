package transactions;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import accounts.Account;
import dao.implementations.TransactionDAOImpl;

/**
 * 
 * @author ripke1tj
 *
 */
public final class TransactionFeeService {

	private TransactionDAOImpl transactionDAOImpl;

	public TransactionFeeService(TransactionDAOImpl transactionDAOImpl) {
		this.transactionDAOImpl = transactionDAOImpl;
	}

	/**
	 * Computes the transaction fee for a given account.
	 * 
	 * @param account: The account to apply the transaction fee.
	 * @param amount:  The amount the transaction fee is calculated on.
	 * @return: The transaction fee.
	 * @throws FileNotFoundException: Thrown if a connection could not be
	 *                                established with the database.
	 */
	public BigDecimal getTransactionFee(Account account, BigDecimal amount) throws FileNotFoundException {
		BigDecimal transactionFee = BigDecimal.ZERO;
		LocalDate firstDayOfMonth;
		LocalDate lastDayOfMonth;
		List<Transaction> transactions;

		switch (account.getAccountType()) {
		case BUSINESS_SAVINGS:
			firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			transactions = transactionDAOImpl.getTransactions(account.getAccountId(), firstDayOfMonth, lastDayOfMonth);
			if (transactions.size() > 10) {
				transactionFee = new BigDecimal("0.25");
			}
			break;
		case BUSINESS_CHECKING:
			firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
			transactions = transactionDAOImpl.getTransactions(account.getAccountId(), firstDayOfMonth, lastDayOfMonth);
			BigDecimal depositSum = BigDecimal.ZERO;
			for (Transaction transaction : transactions) {
				if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
					depositSum.add(transaction.getAmount());
				}
			}
			if (depositSum.compareTo(new BigDecimal("5000.00")) > 0) {
				transactionFee = transactionFee.add(amount.divide(new BigDecimal("100.00"), 15, RoundingMode.HALF_UP)
						.multiply(new BigDecimal("0.30")));
			}

			if (transactions.size() > 100) {
				transactionFee = new BigDecimal("0.50");
			}
			break;
		default:
			break;
		}
		return transactionFee;
	}

}

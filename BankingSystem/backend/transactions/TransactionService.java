package transactions;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import dao.implementations.TransactionDAOImpl;

/**
 * Service class for Transaction.
 * 
 * @author ripke1tj
 *
 */
public class TransactionService {

	private TransactionDAOImpl transactionDAOImpl;

	public TransactionService(TransactionDAOImpl transactionDAOImpl) {
		this.transactionDAOImpl = transactionDAOImpl;
	}

	/**
	 * Retrieves a transaction from the transaction dao.
	 * 
	 * @param transactionId: The transaction id.
	 * @param accountId:     The account id.
	 * @return: A transaction object.
	 * @throws FileNotFoundException: Thrown if a connection could not be
	 *                                established with the database.
	 */
	public Transaction getTransaction(String transactionId, String accountId) throws FileNotFoundException {
		return this.transactionDAOImpl.getTransaction(transactionId, accountId);
	}

	/**
	 * Retrieves transactions for a user given an account id between some start and
	 * end date.
	 * 
	 * @param accountId: The account id.
	 * @param start:     The start date.
	 * @param end:       The end date.
	 * @return: A list of transactions between the specified time period.
	 * @throws FileNotFoundException: Thrown if a connection could not be
	 *                                established with the database.
	 */
	public List<Transaction> getTransactions(String accountId, LocalDate start, LocalDate end)
			throws FileNotFoundException {
		return this.transactionDAOImpl.getTransactions(accountId, start, end);
	}

	/**
	 * Validates and write a transaction to the system.
	 * 
	 * @param transaction: The transaction.
	 * @return: True if successful, false otherwise.
	 * @throws FileNotFoundException: Thrown if a connection could not be
	 *                                established with the database.
	 */
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException {
		return this.transactionDAOImpl.writeTransaction(transaction);
	}

}

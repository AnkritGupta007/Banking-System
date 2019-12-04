package dao.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import transactions.Transaction;

/**
 * Interface for TransactionDAOs
 * 
 * @author ripke1tj
 *
 */
public interface TransactionDAO {

	/**
	 * Retrieves a transaction for an account.
	 * 
	 * @param transactionId: The transaction id.
	 * @param accountId:     The account id.
	 * @return: A specific transaction for the account.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public Transaction getTransaction(String transactionId, String accountId) throws FileNotFoundException;

	/**
	 * Retrieves transactions for a user between some start and end date.
	 * 
	 * @param accountId: The account id.
	 * @param start:     The start date.
	 * @param end:       The end date.
	 * @return: A list of transactions between the specified time period.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Transaction> getTransactions(String accountId, LocalDate start, LocalDate end)
			throws FileNotFoundException;

	/**
	 * Writes a transaction to the system.
	 * 
	 * @param transaction: The transaction to write.
	 * @return: True if successful.
	 */
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException;

	/**
	 * Gets the next available transaction Id.
	 * 
	 * @return: The next available transaction Id.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public static String getNextTransactionId() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("transactions.txt"));
		int counter = 0;
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			counter++;
		}
		scanner.close();
		return String.valueOf(counter);
	}

}

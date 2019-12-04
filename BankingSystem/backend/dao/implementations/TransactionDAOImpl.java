package dao.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dao.interfaces.TransactionDAO;
import transactions.Transaction;

/**
 * Implementation of TransactionDAO.
 * 
 * @author ripke1tj
 *
 */
public class TransactionDAOImpl implements TransactionDAO {

	/*
	 * Used when retrieving transactions between a specified period.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	@Override
	public Transaction getTransaction(String transactionId, String accountId) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("transactions.txt"));
		Transaction transaction = null;
		while (transaction == null || scanner.hasNextLine()) {
			String[] transactionLine = scanner.nextLine().split(",");
			if (transactionId.equals(transactionLine[0]) && accountId.equals(transactionLine[1])) {
				transaction = new Transaction(transactionLine);
			}
		}
		scanner.close();
		return transaction;
	}

	@Override
	public List<Transaction> getTransactions(String accountId, LocalDate start, LocalDate end)
			throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("transactions.txt"));
		List<Transaction> transactions = new ArrayList<Transaction>();
		while (scanner.hasNextLine()) {
			String[] transactionLine = scanner.nextLine().split(",");
			if (accountId.equals(transactionLine[1]) && (LocalDate
					.parse(transactionLine[2], DATE_TIME_FORMATTER_FROM_FILE).equals(LocalDate.now())
					|| (LocalDate.parse(transactionLine[2], DATE_TIME_FORMATTER_FROM_FILE).isAfter(start)
							&& LocalDate.parse(transactionLine[2], DATE_TIME_FORMATTER_FROM_FILE).isBefore(end)))) {
				transactions.add(new Transaction(transactionLine));
			}
		}
		scanner.close();
		return transactions;
	}

	@Override
	public boolean writeTransaction(Transaction transaction) throws FileNotFoundException {
		File file = new File("transactions.txt");
		PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
		pw.println(transaction);
		pw.close();
		return true;
	}

}

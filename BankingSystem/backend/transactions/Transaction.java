package transactions;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import dao.interfaces.TransactionDAO;

/**
 * Represents a transaction in the system.
 * 
 * @author ripke1tj
 *
 */
public class Transaction {

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	private String transactionId;
	private String accountId;
	private LocalDate date;
	private TransactionType transactionType;
	private BigDecimal amount;
	private BigDecimal accountBalance;

	public Transaction(String accountId, LocalDate date, TransactionType transactionType, BigDecimal amount,
			BigDecimal accounBalance) throws FileNotFoundException {
		this.transactionId = TransactionDAO.getNextTransactionId();
		this.accountId = accountId;
		this.date = date;
		this.transactionType = transactionType;
		this.amount = amount;
		this.accountBalance = accounBalance;
	}

	public Transaction(String[] transactionLine) {
		this.transactionId = transactionLine[0];
		this.accountId = transactionLine[1];
		this.date = LocalDate.parse(transactionLine[2], DATE_TIME_FORMATTER_FROM_FILE);
		this.transactionType = TransactionType.valueOf(transactionLine[3]);
		this.amount = new BigDecimal(transactionLine[4]);
		this.accountBalance = new BigDecimal(transactionLine[5]);
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s", this.transactionId, this.accountId, this.date, this.transactionType,
				this.amount, this.accountBalance.toPlainString());
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public LocalDate getDate() {
		return date;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

}

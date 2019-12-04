package dao.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import accounts.Account;
import accounts.AccountType;

/**
 * Interface for AccountDAOs
 * 
 * @author ripke1tj
 *
 */
public interface AccountDAO {

	/**
	 * Retrieves an account given an account number.
	 * 
	 * @param accountNumber: The account number.
	 * @return: The Account expressed as an Account object.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public Account getAccount(String accountNumber) throws FileNotFoundException, ParseException;

	/**
	 * Retrieves all accounts for a user given a drivers license.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: A list of accounts expressed as an Account object.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Account> getUserAccounts(String driversLicense) throws FileNotFoundException, ParseException;

	/**
	 * Retrieves all accounts for a given type.
	 * 
	 * @param type: The Account Type.
	 * @return: A list of accounts.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Account> getAccountsByType(AccountType type) throws FileNotFoundException, ParseException;

	/**
	 * Retrieves all accounts in the system.
	 * 
	 * @return: A list of accounts.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public List<Account> getAllAccounts() throws FileNotFoundException, ParseException;

	/**
	 * Adds an account to the system.
	 * 
	 * @param account: The account to add.
	 * @return: True if successful, false otherwise.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public boolean addAccount(Account account) throws FileNotFoundException;

	/**
	 * Removes an account from the system.
	 * 
	 * @param accountId: The account to remove.
	 * @return: True if successful, false otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean removeAccount(String accountId) throws IOException;

	/**
	 * Updates an account in the system.
	 * 
	 * @param account: The account to update.
	 * @return: True if successful, false otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean updateAccount(Account account) throws IOException;

	/**
	 * Gets the next available account Id.
	 * 
	 * @return: The next available account Id.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public static String getNextAccountId() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("accounts.txt"));
		int counter = 0;
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			counter++;
		}
		scanner.close();
		return String.valueOf(counter);
	}

}

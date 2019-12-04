package dao.implementations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import accounts.Account;
import accounts.AccountType;
import dao.interfaces.AccountDAO;
import dao.interfaces.UserDAO;

/**
 * Implementation of AccountDAO.
 * 
 * @author ripke1tj
 *
 */
public class AccountDAOImpl implements AccountDAO {

	private UserDAO userDAO;

	public AccountDAOImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	
	@Override
	public Account getAccount(String accountNumber) throws FileNotFoundException, ParseException {
		Scanner scanner = new Scanner(new File("accounts.txt"));
		Account account = null;
		while (account == null || scanner.hasNextLine()) {
			String[] accountLine = scanner.nextLine().split(",");
			if (accountNumber.equals(accountLine[0])) {

				account = new Account(accountLine, this.userDAO);
			}
		}
		scanner.close();
		return account;
	}

	
	public List<Account> getUserAccounts(String driversLicense) throws FileNotFoundException, ParseException {
		Scanner scanner = new Scanner(new File("accounts.txt"));
		List<Account> accounts = new ArrayList<Account>();
		while (scanner.hasNextLine()) {
			String[] accountLine = scanner.nextLine().split(",");
			if (driversLicense.equals(accountLine[1])) {
				accounts.add(new Account(accountLine, this.userDAO));
			}
		}
		scanner.close();
		return accounts;
	}

	@Override
	public List<Account> getAccountsByType(AccountType type) throws FileNotFoundException, ParseException {
		Scanner scanner = new Scanner(new File("accounts.txt"));
		List<Account> accounts = new ArrayList<Account>();
		while (scanner.hasNextLine()) {
			String[] accountLine = scanner.nextLine().split(",");
			if (type.equals(AccountType.valueOf(accountLine[3]))) {
				accounts.add(new Account(accountLine, this.userDAO));
			}
		}
		scanner.close();
		return accounts;
	}

	@Override
	public List<Account> getAllAccounts() throws FileNotFoundException, ParseException {
		Scanner scanner = new Scanner(new File("accounts.txt"));
		List<Account> accounts = new ArrayList<Account>();
		while (scanner.hasNextLine()) {
			String[] accountLine = scanner.nextLine().split(",");
			accounts.add(new Account(accountLine, this.userDAO));
		}
		scanner.close();
		return accounts;
	}

	@Override
	public boolean addAccount(Account account) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("accounts.txt"), true));
		writer.println(account);
		writer.close();
		return true;
	}

	@Override
	public boolean removeAccount(String accountId) throws IOException {
		File accountsFile = new File("accounts.txt");
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String id = currentLine.split(",")[0];
			if (id.equals(accountId))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		accountsFile.delete();
		return tempFile.renameTo(accountsFile);
	}

	@Override
	public boolean updateAccount(Account account) throws IOException {
		File accountsFile = new File("accounts.txt");
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String accountId = currentLine.split(",")[0];
			if (accountId.equals(account.getAccountId()))
				writer.write(account.toString() + System.getProperty("line.separator"));
			else {
				writer.write(currentLine + System.getProperty("line.separator"));
			}
		}
		writer.close();
		reader.close();
		accountsFile.delete();
		return tempFile.renameTo(accountsFile);
	}

}

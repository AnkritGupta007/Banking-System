package accounts;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import dao.interfaces.AccountDAO;
import dao.interfaces.UserDAO;
import users.User;

/**
 * Represents an account in the system.
 * 
 * @author ripke1tj
 *
 */
public class Account {

	private String accountId;
	private String driversLicense;
	private BigDecimal accountBalance;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private boolean ableToOverDraft;
	private List<User> authorizedUsers = new ArrayList<User>();

	public Account(String driversLicense, AccountType accountType, boolean ableToOverDraft, User... authorizedUsers)
			throws FileNotFoundException {
		this.accountId = AccountDAO.getNextAccountId();
		this.driversLicense = driversLicense;
		this.accountBalance = BigDecimal.ZERO;
		this.accountType = accountType;
		this.accountStatus = AccountStatus.ACTIVE;
		this.ableToOverDraft = ableToOverDraft;
		this.authorizedUsers = Arrays.asList(authorizedUsers);
	}

	public Account(String accountId, String driversLicense, BigDecimal accountBalance, AccountType accountType,
			boolean ableToOverDraft, User... authorizedUsers) {
		this.accountId = accountId;
		this.driversLicense = driversLicense;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
		this.accountStatus = AccountStatus.ACTIVE;
		this.ableToOverDraft = ableToOverDraft;
		this.authorizedUsers = Arrays.asList(authorizedUsers);
	}

	public Account(String[] accountLine, UserDAO userDAO) throws FileNotFoundException, ParseException {
		this.accountId = accountLine[0];
		this.driversLicense = accountLine[1];
		this.accountBalance = new BigDecimal(accountLine[2]);
		this.accountType = AccountType.valueOf(accountLine[3]);
		this.accountStatus = AccountStatus.valueOf(accountLine[4]);
		this.ableToOverDraft = Boolean.valueOf(accountLine[5]);
		for (int i = 6; i < accountLine.length; i++) {
			this.authorizedUsers.add(userDAO.getUser(accountLine[i]));
		}
	}

	public Account(String driversLicense, AccountType accountType, User authorizedUser, boolean ableToOverDraft)
			throws FileNotFoundException {
		this.accountId = AccountDAO.getNextAccountId();
		this.accountBalance = BigDecimal.ZERO;
		this.driversLicense = driversLicense;
		this.accountType = accountType;
		this.authorizedUsers.add(authorizedUser);
		this.accountStatus = AccountStatus.ACTIVE;
	}

	@Override
	public String toString() {
		String authorizedUsers = "";
		Iterator<User> it = this.authorizedUsers.iterator();
		if (it.hasNext()) {
			authorizedUsers = it.next().getDriversLicense();
		}
		while (it.hasNext()) {
			authorizedUsers = authorizedUsers + ", " + it.next().getDriversLicense();
		}
		return String.format("%s,%s,%s,%s,%s,%s,%s", this.accountId, this.driversLicense,
				this.accountBalance.toPlainString(), this.accountType.toString(), this.accountStatus.toString(),
				this.ableToOverDraft, authorizedUsers);
	}

	public String getAccountId() {
		return String.valueOf(accountId);
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public boolean isAbleToOverDraft() {
		return ableToOverDraft;
	}

	public void setAbleToOverDraft(boolean ableToOverDraft) {
		this.ableToOverDraft = ableToOverDraft;
	}

}

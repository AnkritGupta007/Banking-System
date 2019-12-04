package accounts;

import java.io.FileNotFoundException;

import users.User;

/**
 * Used to return a new Account.
 * 
 * @author ripke1tj
 *
 */
public final class AccountFactory {

	private AccountFactory() {
		throw new IllegalStateException("This class should not be instantiated.");
	}

	/**
	 * Used to return a new Account with the user and type set.
	 * 
	 * @param accountType:     The type of account to create.
	 * @param user:            The user to create the account for.
	 * @param authorizedUsers: If required, an authorized user for the account.
	 * @return: The constructed Account object.
	 * @throws FileNotFoundException
	 */
	public static Account getAccount(AccountType accountType, User user, boolean ableToOverDraft,
			User... authorizedUsers) throws FileNotFoundException {
		Account account = null;
		switch (accountType) {
		case STUDENT_CHECKING:
			if (user.getAge() >= 17 && user.getAge() <= 23) {
				account = new Account(user.getDriversLicense(), accountType, ableToOverDraft);
			} else if (user.getAge() >= 12 && user.getAge() < 17) {
				boolean flag = false;
				for (User authorizedUser : authorizedUsers) {
					if (authorizedUser.getAge() >= 18) {
						account = new Account(user.getDriversLicense(), accountType, authorizedUser, ableToOverDraft);
						flag = true;
						break;
					}
				}
				if (flag == false) {
					throw new IllegalStateException("Cannot create " + accountType + " for " + user.getDriversLicense()
							+ ". No eligible authorized user.");
				}
			} else {
				throw new IllegalStateException("Cannot create " + accountType + " for " + user.getDriversLicense()
						+ ". Age is not between 17 and 23: " + user.getAge());
			}
			break;
		case STUDENT_SAVINGS:
			if (user.getAge() >= 17 && user.getAge() <= 23) {
				account = new Account(user.getDriversLicense(), accountType, ableToOverDraft);
			} else if (user.getAge() >= 12) {
				boolean flag = false;
				for (User authorizedUser : authorizedUsers) {
					if (authorizedUser.getAge() >= 18) {
						account = new Account(user.getDriversLicense(), accountType, authorizedUser, ableToOverDraft);
						flag = true;
						break;
					}
				}
				if (flag == false) {
					throw new IllegalStateException("Cannot create " + accountType + " for " + user.getDriversLicense()
							+ ". Age is not between 17 and 23: " + user.getAge() + ". No eligible authorized user.");
				}
			} else {
				throw new IllegalStateException("Cannot create " + accountType + " for " + user.getDriversLicense()
						+ ". Age is not between 17 and 23: " + user.getAge());
			}
			break;
		case PERSONAL_CHECKING:
		case BUSINESS_CHECKING:
		case PERSONAL_SAVINGS:
		case BUSINESS_SAVINGS:
			account = new Account(user.getDriversLicense(), accountType, ableToOverDraft);
			break;
		default:
			throw new IllegalStateException("Unable to create account with account type: " + accountType.toString());
		}
		return account;
	}

}

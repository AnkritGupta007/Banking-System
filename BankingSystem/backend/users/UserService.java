package users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import dao.implementations.AccountDAOImpl;
import dao.implementations.UserDAOImpl;

/**
 * Service class for User.
 * 
 * @author ripke1tj
 *
 */
public class UserService {

	private UserDAOImpl userDAO;
	private AccountDAOImpl accountDAOImpl;

	public UserService(UserDAOImpl userDAO, AccountDAOImpl accountDAOImpl) {
		this.userDAO = userDAO;
		this.accountDAOImpl = accountDAOImpl;
	}

	/**
	 * Adds a user to the system.
	 * 
	 * @param user: The user to add.
	 * @return: True if successful, false otherwise.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public boolean addUser(User user) throws FileNotFoundException {
		return this.userDAO.addUser(user);
	}

	/**
	 * Validates and adds a user to the system.
	 * 
	 * @param driversLicense: The drivers license.
	 * @param firstName:      The first name.
	 * @param middleName:     The middle name.
	 * @param lastName:       The last name.
	 * @param occupation:     The occupation.
	 * @param dateOfBirth:    The date of birth (MM/DD/YYYY).
	 * @return: True if successful, false otherwise.
	 * @throws FileNotFoundException:    Thrown if a connection to the database
	 *                                   could not be established.
	 * @throws IllegalArgumentException: Thrown if a required parameter is not
	 *                                   present.
	 */
	public boolean addUser(String driversLicense, String firstName, String middleName, String lastName,
			String occupation, String dateOfBirth) throws FileNotFoundException, IllegalArgumentException {

		if (driversLicense == null || driversLicense.isEmpty()) {
			throw new IllegalArgumentException("Driver's license required");
		} else if (firstName == null || firstName.isEmpty()) {
			throw new IllegalArgumentException("First name required");
		} else if (lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException("Last name required");
		} else if (occupation == null || occupation.isEmpty()) {
			throw new IllegalArgumentException("Occupation required");
		} else if (dateOfBirth == null || dateOfBirth.isEmpty()) {
			throw new IllegalArgumentException("Date of birth required");
		} else if (!dateOfBirth.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
			throw new IllegalArgumentException("Date of birth invalid");
		}
		return this.userDAO.addUser(new User(driversLicense, firstName, middleName, lastName, occupation, dateOfBirth));

	}

	/**
	 * Removes a user from the system if they have closed their accounts.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: True if successful, false otherwise.
	 * @throws IOException:    Thrown if a required parameter is not present.
	 * @throws ParseException: Thrown if there is an issue parsing the birth date of
	 *                         the user.
	 */
	public boolean removeUser(String driversLicense) throws IOException, ParseException {
		if (accountDAOImpl.getUserAccounts(driversLicense).size() != 0) {
			throw new IllegalStateException("Cannot remove user without first closing all accounts");
		}
		return this.userDAO.removeUser(driversLicense);
	}

}

package dao.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import users.User;

public interface UserDAO {

	/**
	 * Searches for and creates a User from a comma separated list of attributes.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return A User object
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 * @throws ParseException:        Thrown if there was an issue parsing the date
	 *                                of birth
	 */
	public User getUser(String driversLicense) throws FileNotFoundException, ParseException;

	/**
	 * Writes a user object to a file as a comma separated list of attributes.
	 * 
	 * @param user: The user to write
	 * @throws FileNotFoundException: Thrown if the file was not found
	 */
	public boolean addUser(User user) throws FileNotFoundException;

	/**
	 * Removes a user from the system given a drivers license.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: True if successful, false otherwise.
	 * @throws IOException: Thrown if a connection to the database could not be
	 *                      established.
	 */
	public boolean removeUser(String driversLicense) throws IOException;
}

package dao.interfaces;

import java.io.FileNotFoundException;

import users.Address;

/**
 * Interface for AddressDAOs
 * 
 * @author ripke1tj
 *
 */
public interface AddressDAO {

	/**
	 * Retrieves an address from the system given a drivers License.
	 * 
	 * @param driversLicense: The drivers license.
	 * @return: An Address object.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public Address getAddress(String driversLicense) throws FileNotFoundException;

	/**
	 * Writes an address to the system given an Address object.
	 * 
	 * @param address: The Address to write.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public void writeAddress(Address address) throws FileNotFoundException;

}

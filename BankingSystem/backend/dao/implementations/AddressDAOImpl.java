package dao.implementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import dao.interfaces.AddressDAO;
import users.Address;

/**
 * Implementation of AddressDAO.
 * 
 * @author ripke1tj
 *
 */
public class AddressDAOImpl implements AddressDAO {

	@Override
	public Address getAddress(String driversLicense) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("addresses.txt"));
		Address address = null;
		while (address == null || scanner.hasNextLine()) {
			String[] addressLine = scanner.nextLine().split(",");
			if (driversLicense.equals(addressLine[0])) {
				address = new Address(addressLine);
			}
		}
		scanner.close();
		return address;
	}

	@Override
	public void writeAddress(Address address) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("addresses.txt"), true));
		writer.println(address);
		writer.close();
	}

}

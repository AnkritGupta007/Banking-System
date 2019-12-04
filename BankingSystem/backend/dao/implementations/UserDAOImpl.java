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
import java.util.Scanner;

import dao.interfaces.UserDAO;
import users.User;

/**
 * Implementation of UserDAO.
 * 
 * @author ripke1tj
 *
 */
public class UserDAOImpl implements UserDAO {

	@Override
	public User getUser(String driversLicense) throws FileNotFoundException, ParseException {
		Scanner scanner = new Scanner(new File("users.txt"));
		User user = null;
		while (user == null || scanner.hasNextLine()) {
			String[] userLine = scanner.nextLine().split(",");
			if (driversLicense.equals(userLine[0])) {
				user = new User(userLine);
			}
		}
		scanner.close();
		return user;
	}

	@Override
	public boolean addUser(User user) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("users.txt"), true));
		writer.println(user);
		writer.close();
		return true;
	}

	@Override
	public boolean removeUser(String driversLicense) throws IOException {
		File usersFile = new File("users.txt");
		File tempFile = new File("temp.txt");
		BufferedReader reader = new BufferedReader(new FileReader(usersFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.split(",")[0];
			if (trimmedLine.equals(driversLicense))
				continue;
			writer.write(currentLine + System.getProperty("line.separator"));
		}
		writer.close();
		reader.close();
		usersFile.delete();
		return tempFile.renameTo(usersFile);

	}

}

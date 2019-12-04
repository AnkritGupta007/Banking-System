package users;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a User account.
 * 
 * @author ripke1tj
 *
 */
public class User {

	/*
	 * Used to parse the current date of the transaction into a LocalDate object.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/*
	 * Used to parse the date read from the database.
	 */
	private static final DateTimeFormatter DATE_TIME_FORMATTER_FROM_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd",
			Locale.ENGLISH);

	private String driversLicense;
	private String firstName;
	private String middleName;
	private String lastName;
	private String occupation;
	private LocalDate dateOfBirth;

	public User(String driversLicense, String firstName, String middleName, String lastName, String occupation,
			String dateOfBirth) {
		this.driversLicense = driversLicense;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.occupation = occupation;
		this.dateOfBirth = LocalDate.parse(dateOfBirth, DATE_TIME_FORMATTER);
	}

	public User(String[] userFromFile) throws ParseException {
		this.driversLicense = userFromFile[0];
		this.firstName = userFromFile[1];
		this.middleName = userFromFile[2];
		this.lastName = userFromFile[3];
		this.occupation = userFromFile[4];
		this.dateOfBirth = LocalDate.parse(userFromFile[5], DATE_TIME_FORMATTER_FROM_FILE);
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s", this.driversLicense, this.firstName, this.middleName, this.lastName,
				this.occupation, this.dateOfBirth);
	}

	public int getAge() {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

	public String getDriversLicense() {
		return driversLicense;
	}

	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}

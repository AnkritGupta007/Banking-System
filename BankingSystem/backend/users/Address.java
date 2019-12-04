package users;

/**
 * Represents an address for a User.
 * 
 * @author ripke1tj
 *
 */
public class Address {

	private String driversLicense;
	private String street;
	private String city;
	private State state;
	private String zip;

	public Address(String driversLicense, String street, String city, String state, String zip) {
		this.driversLicense = driversLicense;
		this.street = street;
		this.city = city;
		this.state = State.parse(state);
		this.zip = zip;
	}

	public Address(String[] addressFromFile) {
		this.driversLicense = addressFromFile[0];
		this.street = addressFromFile[1];
		this.city = addressFromFile[2];
		this.state = State.parse(addressFromFile[3]);
		this.zip = addressFromFile[4];
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s", this.driversLicense, this.street, this.city, this.state.ANSIabbreviation,
				this.zip);
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}

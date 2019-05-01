package InformationManagement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * A user from the database
 * @author ChangTan
 */
public class User implements LibraryInformation {
	
	private int id;
	private String username;
	private String firstname;
	private String surname;
	private String houseNumber;
	private String streetName;
	private String postCode;
	private String email;
	private Date birthday;
	private String password;
	
	//Constructors
	
	/**
	 * Create a new User which can borrow books from the library.
	 */
	public User(int id, String username, String password, String firstname, String surname, String houseNumber, String streetName, String postCode,
			String email, Date birthday) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
		this.houseNumber = houseNumber == null? "Unnumbered" : houseNumber;
		this.streetName = streetName;
		this.postCode = postCode;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
	}

	/**
	 * Creates a new User which can borrow books from the library, without specifying the id.
	 * 
	 * @param username The username of the user. It is unique.
	 * @param password The password is just used for generating the insert statement. 
	 * It will be stored with an encoding. If it is null, the user will not be able to generate an insert statement.
	 * @param firstname The firstname of the user.
	 * @param surname The lastname of the user.
	 * @param houseNumber The house number of the user. It may be a String (as 24A)
	 * @param streetName The street name of the user.
	 * @param postCode The postcode of the user.
	 * @param email The email of the user.
	 * @param birthday The birthday of the user.
	 */
	public User(String username, String password, String firstname, String surname, String houseNumber, String streetName, String postCode,
			String email, Date birthday)
	{
		this(0, username, password, firstname, surname, houseNumber, streetName, postCode,
				 email, birthday);
	}
	
	/**
	 * Gets the id of the user.
	 * @return the id of the user.
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * Returns the username of the user. It is unique.
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the firstname of the user.
	 * @return the firstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Returns the surname of the user.
	 * @return the surname.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Returns the house number of the user.
	 * @return the house number.
	 */
	public String getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Returns the street name of the user.
	 * @return the street name.
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * Returns the postcode of the user.
	 * @return the postcode.
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Returns the email of the user.
	 * @return the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the birthday of the user.
	 * @return the birthday.
	 */
	public Date getBirthday() {
		return birthday;
	}

	
	//override method  -- see next class.
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User uObj = (User)obj;
			return uObj.getUsername().equalsIgnoreCase(this.getUsername());
		}
		return false;
	}

	@Override
	public String insertInformation() {
		if(password != null)
		{
			String statement = "INSERT INTO "
					+ "users (username, password, firstname, surname, "
					+ "house_number, street_name, postcode, email, birthday) "
					+ "VALUES ('"
					+ username + "', '"
					+ password + "', '"
					+ firstname + "', '"
					+ surname + "', '"
					+ houseNumber + "', '"
					+ streetName + "', '"
					+ postCode + "', '"
					+ email + "', '"
					+ getFormatedBirthday() + "')";
			return statement;
		}
		return null;
	}

	@Override
	public String updateInformation(String userName) {
		
		String statement = "UPDATE users SET ";
		statement += "username = '" + username + "', ";
		if(password != null)
			statement += "password = '" + password + "', ";
		statement += "firstname = '" + firstname + "', "
				+ "surname = '" + surname + "', "
				+ "house_number = '" + houseNumber + "', "
				+ "street_name = '" + streetName + "', "
				+ "postcode = '" + postCode + "', "
				+ "email = '" + email + "', "
				+ "birthday = '" + getFormatedBirthday() + "' "
				+ "WHERE username = '" + userName + "'";
		
		return statement;
	}

	@Override
	public String deleteStatement() {
		String statement = "DELETE FROM users WHERE username = '" + username + "'";
		return statement;
	}
	
	/**
	 * Gets the birthday in the format yyyy-mm-dd
	 * @return the formated birthday
	 */
	private String getFormatedBirthday() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		return simpleDateFormat.format(birthday);
	}
	
	@Override
	public String toString() {
		return firstname + " " + surname + ". Email: " + email; 
	}

	
	
}

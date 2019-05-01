package components;

import  java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import InformationManagement.User;

/**
 * A collection of users, where they can be added, filtered or removed. 
 * 
 * @author ChangTan
 */
public class UserList extends LibraryList<User> {

	//Constructors

	// A new UserList with the users in the database

	public UserList() throws SQLException {
		super(User.class);
	}

	/**
	 * A new list cloning the elements from the list
	 */
	public UserList(List<User> list) {
		super(list);
	}

	/**
	 * A new list cloning the elements from the user list
	 */
	public UserList(UserList userList) {
		super(userList);
	}


	/**
	 * Returns a UserList of users that have the same name.
	 *
	 * @param firstname The firstname of the user.
	 * @return a UserList of users containing the looked firstname.
	 */
	public UserList searchUsersByFirstname(String firstname) {
		return new UserList((ArrayList<User>) list.stream().filter(u -> u.getFirstname().toLowerCase().contains(firstname.toLowerCase()))
				.collect(Collectors.toList()));
	}

	/**
	 * Returns a UserList of users looking by its username.
	 *
	 * @param username The username of the user.
	 * @return a UserList of users containing the looked username.
	 */
	public UserList searchUsersByUsername(String username) {
		return new UserList((ArrayList<User>) list.stream().filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
				.collect(Collectors.toList()));
	}

	/**
	 * Returns a UserList of users looking by its surname.
	 *
	 * @param surname The surname of the user.
	 * @return a UserList of users containing the looked surname.
	 */
	public UserList searchUsersBySurname(String surname) {
		return new UserList((ArrayList<User>) list.stream().filter(u -> u.getSurname().toLowerCase().contains(surname.toLowerCase()))
				.collect(Collectors.toList()));
	}

	/**
	 * Returns a UserList of users looking by its email.
	 *
	 * @param email The email of the user.
	 * @return a UserList of users containing the looked email.
	 */
	public UserList searchUsersByEmail(String email) {
		return new UserList((ArrayList<User>) list.stream().filter(u -> u.getEmail().toLowerCase().contains(email))
				.collect(Collectors.toList()));
	}

}

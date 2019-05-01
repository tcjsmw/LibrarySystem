package components;

import java.sql.SQLException;

import InformationManagement.User;

/**
 * The table model for the table that will display users in the administrator panel
 * @author ChangTan
 */
public class UsersTable extends AdministratorTable {

	private static final long serialVersionUID = 2L;

	private static final String[] USER_COLUMNS = {"Username", "Firstname", "Lastname", "Email", "Books on loan"};
	
	//Constructors
	
	/**
	 * Creates a table model for the users in the administrator panel
	 * @param userList the list of users to be displayed
	 */
	public UsersTable(UserList userList) {
		super(USER_COLUMNS, userList);
	}

	//public methods
	
	@Override
	public Object getValueAt(int row, int col) {
		User user = ((UserList)libraryList).getList().get(row);
		switch (col) {
		case 0:
			return user.getUsername();
		case 1:
			return user.getFirstname();
		case 2:
			return user.getSurname();
		case 3:
			return user.getEmail();
		case 4:
			try {
				return Loans.getLoansByUser(user).size();
			} catch (SQLException e) {
				return 0;
			}
		default:
			return null;
		}
	}
}

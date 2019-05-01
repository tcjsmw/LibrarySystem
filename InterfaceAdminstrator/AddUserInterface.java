package InterfaceAdminstrator;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DatabaseOperation;
import InformationManagement.User;

/**
 * A dialog to add a user
 * @author ChangTan
 */
public class AddUserInterface extends AbstractUserInterface {

	private static final long serialVersionUID = 8930103170251788540L;

	private boolean added = false;
	
	//Constructors
	
	/**
	 * Creates a dialog with empty fields for adding a user.
	 */
	public AddUserInterface() {
		super(false);
		setTitle("Add user");
	}
	
	//public methods
	
	/**
	 * Indicates if the user was added successfully.
	 * @return true if the user was added successfully.
	 */
	public boolean getAdded() {
		return this.added;
	}

	//protected methods
	
	/**
	 * Adds the user to the database.
	 * 
	 * @param user the user to add to the database.
	 */
	@Override
	protected void performAction(User user) {
		if(user != null)
		{
			try {
				DatabaseOperation databaseOperation = new DatabaseOperation();
				if(databaseOperation.insertInformation(user))
				{
					JOptionPane.showMessageDialog(this, "User added successfuly", "Error", JOptionPane.INFORMATION_MESSAGE);
					added = true;
					close();
				}
				else {
					JOptionPane.showMessageDialog(this, "There was a problem adding this user. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "It was not possible to stablish a connection with the database", "Error", JOptionPane.ERROR_MESSAGE);
				close();
			}
		}
	}
	
}

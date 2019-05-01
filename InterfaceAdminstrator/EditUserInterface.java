package InterfaceAdminstrator;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DatabaseOperation;
import InformationManagement.User;

/**
 * A dialog for editing a user
 * @author ChangTan
 */
public class EditUserInterface extends AbstractUserInterface {

	private static final long serialVersionUID = -8715946550758903132L;

	private User user;
	
	private boolean edited;
	
	//Constructors
	
	/**
	 * Creates a dialog to edit a given user
	 * 
	 * @param user the user to edit
	 */
	public EditUserInterface(User user) {
		super(true);
		setTitle("Edit user " + user.getUsername());
		this.user = user;
		super.fillData(user);
	}
	
	//public methods
	
	/**
	 * Indicates if the user was edited
	 * @return true if the user was edited.
	 */
	public boolean getEdited() {
		return this.edited;
	}

	//protected methods
	
	/**
	 * Edits the given user
	 */
	@Override
	protected void performAction(User user) {
		if(user != null)
		{
			try {
				DatabaseOperation databaseOperation = new DatabaseOperation();
				int selection = JOptionPane.showConfirmDialog(this, "Do you want to edit this user?", "Confirm action", JOptionPane.YES_NO_CANCEL_OPTION);
				if(selection == JOptionPane.YES_OPTION)
				{
					if(databaseOperation.updateInformation(user, this.user.getUsername()))
					{
						JOptionPane.showMessageDialog(this, "User edited successfuly", "Error", JOptionPane.INFORMATION_MESSAGE);
						edited = true;
						close();
					}
					else {
						JOptionPane.showMessageDialog(this, "There was a problem editing this user. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if(selection == JOptionPane.NO_OPTION) {
					close();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "It was not possible to stablish a connection with the database", "Error", JOptionPane.ERROR_MESSAGE);
				close();
			}
		}
	}
	
}
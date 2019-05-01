package InterfaceAdminstrator;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DatabaseOperation;
import InformationManagement.Book;

/**
 * A dialog for adding a book.
 * @author ChangTan
 */
public class AddBookInterface extends AbstractBookInterface {

	private static final long serialVersionUID = 5511733809995732661L;

	private boolean added = false;
	
	//Constructors
	/**
	 * Creates a dialog with empty fields for adding a book.
	 */
	public AddBookInterface() {
		super(false, Book.generateRandomId());
		setTitle("Add book");
	}

	//public methods
	
	/**
	 * Indicates if the book was added successfully.
	 * @return true if the book was added successfully.
	 */
	public boolean getAdded() {
		return this.added;
	}
	
	//protected methods
	/**
	 * Adds the book to the database along with its authors.
	 * 
	 * @param book the book to add to the database.
	 */
	@Override
	protected void performAction(Book book) {
		if(book != null)
			{
			try {
				DatabaseOperation databaseOperation = new DatabaseOperation();
				if(databaseOperation.insertInformation(book))
				{
					JOptionPane.showMessageDialog(this, "Book added successfuly", "Error", JOptionPane.INFORMATION_MESSAGE);
					added = true;
					close();
				}
				else {
					JOptionPane.showMessageDialog(this, "There was a problem adding this book. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "It was not possible to stablish a connection with the database", "Error", JOptionPane.ERROR_MESSAGE);
				close();
			}
		}
	}
	
}

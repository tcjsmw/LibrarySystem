package InterfaceAdminstrator;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DatabaseOperation;
import InformationManagement.Book;

/**
 * A dialog for editing books.
 * @author ChangTan
 */
public class EditBookInterface extends AbstractBookInterface {

	private static final long serialVersionUID = -4061589738104556701L;

	private Book book;
	
	private boolean edited = false;
	
	//Constructors

	/**
	 * Creates a dialog for editing a given book
	 * @param book the book to edit
	 */
	public EditBookInterface(Book book) {
		super(true, book.getId());
		setTitle("Edit book " + book.getId());
		this.book = book;
		super.fillData(book);
	}
	
	//public methods
	
	/**
	 * Indicates if the book was edited
	 * @return true if the book was edited.
	 */
	public boolean getEdited() {
		return this.edited;
	}

	//protected methods
	@Override
	/**
	 * Edits the book
	 */
	protected void performAction(Book book) {
		if(book != null)
		{
			try {
				DatabaseOperation databaseOperation = new DatabaseOperation();
				int selection = JOptionPane.showConfirmDialog(this, "Do you want to edit this book?", "Confirm action", JOptionPane.YES_NO_CANCEL_OPTION);
				if(selection == JOptionPane.YES_OPTION)
				{
					if(databaseOperation.updateInformation(book, this.book.getId() + ""))
					{
						JOptionPane.showMessageDialog(this, "Book edited successfuly", "Error", JOptionPane.INFORMATION_MESSAGE);
						edited = true;
						close();
					}
					else {
						JOptionPane.showMessageDialog(this, "There was a problem editing this book. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
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

package components;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import database.DatabaseOperation;
import InformationManagement.Book;
import InformationManagement.Loan;
import InformationManagement.User;

/**
 * Static class with methods related to loans.
 * 
 * @author ChangTan
 */
public final class Loans {
	
	//Constructor
	/**
	 * Private constructor to avoid external instantiating.
	 */
	private Loans() {}
	
	//public methods
	

	 // Adds a loan to the given user and book

	public static int borrowABook(User user, Book book) throws SQLException {
		DatabaseOperation databaseOperation = new DatabaseOperation();
		if(databaseOperation.getRestCopies(book.getId()) > 0) {
			if(databaseOperation.ifUserCanBorrow(user.getUsername(), book.getId())) {
				Calendar calendar = Calendar.getInstance();
				Date borrowDate = calendar.getTime();
				calendar.add(Calendar.MONTH, 1);
				Date dueDate = calendar.getTime();
				Loan loan = new Loan(book, user, borrowDate, dueDate);
				if(databaseOperation.insertInformation(loan))
					return 1;
				else 
					return 0;
			}
			else {
				return -2;
			}
		}
		else {
			return -1;
		}
	}


	 //Removes a loan in the database given a user and a book

	public static boolean returnABook(User user, Book book) throws SQLException{
		DatabaseOperation databaseOperation = new DatabaseOperation();
		Loan loan = databaseOperation.getLoan(book.getId(), user.getId());
		return databaseOperation.deleteInformation(loan);
	}

	

	 // Gets the loans related to a user

	public static LoanList getLoansByUser(int id) throws SQLException{
		DatabaseOperation databaseOperation = new DatabaseOperation();
		return new LoanList(databaseOperation.getLoansAccordingUser(id));
	}
	

	 //Gets the loans related to a user

	public static LoanList getLoansByUser(User user) throws SQLException{
		return getLoansByUser(user.getId());
	}

	//Gets the loans related to a book

	public static LoanList getLoansByBook(int id) throws SQLException{
		DatabaseOperation databaseOperation = new DatabaseOperation();
		return new LoanList(databaseOperation.getLoansAccordingBook(id));
	}
	

	 // Gets the loans related to a book

	public static LoanList getLoansByBook(Book book) throws SQLException{
		return getLoansByBook(book.getId());
	}
}

package InformationManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * A representation of a Loan
 * @author ChangTan
 */
public class Loan implements LibraryInformation {
	
	private int id;
	private Book book;
	private User user;
	private Date borrowDate;
	private Date dueDate;
	
	//Constructors
	
	/**
	 * Constructor of a loan with all the required information
	 * 
	 * @param id the id of the loan
	 * @param book the book loaned
	 * @param user the user loaning
	 * @param borrowDate the date when the book was borrowed
	 * @param dueDate the date when the book is intended to be returned
	 */
	public Loan(int id, Book book, User user, Date borrowDate, Date dueDate) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
	}
	

	 // Constructor of a loan without providing an id

	public Loan(Book book, User user, Date borrowDate, Date dueDate) {
		this(0, book, user, borrowDate, dueDate);
	}
	
	//public methods
	
	/**
	 * Gets the id of the loan
	 * @return the id of the loan. 0 if not specified
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the loaned book
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * Sets the loaned book
	 * @param book the loaned book
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * Gets the loaning user
	 * @return the loaning user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the loaning user
	 * @param user the loaning user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the borrowed date
	 * @return the borrowed date
	 */
	public Date getBorrowDate() {
		return borrowDate;
	}

	/**
	 * Gets the due date
	 * @return the due date
	 */
	public Date getDueDate() {
		return dueDate;
	}
	

	@Override
	public String insertInformation() {
		String statement = "INSERT INTO "
				+ "loans (id_user, id_book, borrowed_date, due_date) "
				+ "VALUES ("
				+ user.getId() + ", "
				+ book.getId() + ", '"
				+ getFormatedDate(borrowDate) + "', '"
				+ getFormatedDate(dueDate) + "')";
		return statement;
	}

	@Override
	public String updateInformation(String oldId) {
		String statement = "UPDATE loans SET "
				+ "id_book = " + book.getId() + ", "
				+ "id_user = " + user.getId() + ", "
				+ "borrowed_date = '" + getFormatedDate(borrowDate) + "', "
				+ "due_date = '" + getFormatedDate(dueDate) + "' "
				+ "WHERE id = " + oldId;
		return statement;
	}

	@Override
	public String deleteStatement() {
		String statement = "DELETE FROM loans WHERE id_user = " + user.getId() + " AND id_book = " + book.getId();
		return statement;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Loan) {
			return ((Loan)obj).id == this.id;
		}
		else
			return false;
	}
	
	/**
	 * Determines if the loan is overdue depending on the reference day
	 * @param today the day where the due date will be compared
	 * @return true if the book is overdue
	 */
	public boolean isOverdue(Date today) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int tYear = calendar.get(Calendar.YEAR);
		int tMonth = calendar.get(Calendar.MONTH);
		int tDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.set(tYear, tMonth, tDay);
		Date _today = calendar.getTime();
		
		calendar.setTime(dueDate);
		int dYear = calendar.get(Calendar.YEAR);
		int dMonth = calendar.get(Calendar.MONTH);
		int dDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.set(dYear, dMonth, dDay);
		Date _dueDate = calendar.getTime();
		
		return _dueDate.getTime() < _today.getTime();
	}


	/**
	 * Gets a representation of the loan used by the user
	 * @return a user useful representation of the loan
	 */
	public String getForUserRepresentation()
	{
		return book.getTitle() + ". Borrowed: " + 
				getFormatedDate(borrowDate) + ". Due: " + getFormatedDate(dueDate);
	}

	//private methods
	
	/**
	 * Gets the formated date
	 * @param date the date to format
	 * @return the formated date in the form yyyy-mm-dd
	 */
	private static String getFormatedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		return simpleDateFormat.format(date);
	}
}

package database;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.sql.ResultSetMetaData;

import InformationManagement.*;
import InformationManagement.LibraryInformation;

/**
 * This class handles connections to the database and provides operations methods.
 * @author ChangTan
 */
public class DatabaseOperation {
	
	private DatabaseConnection databaseConnection;
	private SimpleDateFormat simpleDateFormat;
	
	//Prefabricated query
	private static final String LOAN_SELECT = "SELECT "
			+ "loans.id AS id, loans.id_user AS id_user, loans.id_book AS id_book, loans.borrowed_date, "
			+ "loans.due_date, "
			+ "users.username, users.firstname, users.surname, "
			+ "users.house_number, users.street_name, users.postcode,"
			+ "users.email, users.birthday, "
			+ "books.title, books.year, books.publisher, books.copies, "
			+ "books.publication_date "
			+ "FROM loans "
			+ "INNER JOIN users ON users.id = loans.id_user "
			+ "INNER JOIN books ON books.id = loans.id_book ";
	
	private static final String USER_SELECT = "SELECT "
			+ "users.id AS id_user, users.username, users.firstname, users.surname, "
			+ "users.house_number, users.street_name, users.postcode,"
			+ "users.email, users.birthday, users.password "
			+ "FROM users ";
	
	private static final String BOOK_SELECT = "SELECT "
			+ "books.id AS id_book, books.title, books.year, books.publisher, books.copies, "
			+ "books.publication_date "
			+ "FROM books ";
	
	//Constructors

	public DatabaseOperation() throws SQLException{
		databaseConnection = DatabaseConnection.getInstance();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
	}
	
	//public methods
	
	/**
	 *  Use the username and password in the database to determine if the user input is correct.
	 */
	public User loginUser(String username, String password)
	{
		String query = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s'",
									username, password);
		return (User)getSingleObject(query, User.class);
	}
	
	/**
	 * Use the username and password in the database to determine if the administrator input is correct.
	 */
	public boolean loginAdministrator(String username, String password) throws SQLException
	{
		try {
			String query = String.format("SELECT * FROM administrators WHERE username = '%s' AND password = '%s'", username, password);
			ResultSet resultSet = databaseConnection.getResult(query);
			boolean exists = resultSet.next();
			return exists;		
		} catch (SQLException e) {
			throw e;
		} 
	}

	/**
	 * Inserts information to the database.
	 */
	public boolean insertInformation(LibraryInformation element) {
		if(element instanceof Book)
		{
			if(executeUpdateQuery(element.insertInformation()) > 0)
			{
				Book book = (Book)element;
				for(Author author : book.getAuthors())
				{
					if(!insertInformation(author))
					{
						deleteInformation(book);
						return false;
					}
				}
				return true;
			}
			else {
				return false;
			}
		}
		return executeUpdateQuery(element.insertInformation()) > 0;
	}
	
	/**
	 * Updates an element within the database.
	 */
	public boolean updateInformation(LibraryInformation element, String id) {
		if(element instanceof Book) {
			Book book = (Book)element;
			if(executeUpdateQuery("DELETE FROM authors WHERE idbook = " + id) > 0)
			{
				for(Author author : book.getAuthors())
				{
					if(!insertInformation(author))
					{
						return false;
					}
				}
			}
			else
				return false;
		}
		return executeUpdateQuery(element.updateInformation(id)) > 0;
	}
	
	/**
	 * Deletes informationin the database
	 */
	public boolean deleteInformation(LibraryInformation element) {
		return executeUpdateQuery(element.deleteStatement()) > 0;
	}
		
	/**
	 * Gets a list of all LibraryInformation items from the database.
	 */
	public List<LibraryInformation> getList(Class<?> c) {
		if(c.equals(Book.class)) {
			return getList(BOOK_SELECT, c);
		}
		else if(c.equals(User.class)) {
			return getList(USER_SELECT, c);
		}
		else if(c.equals(Loan.class)) {
			return getList(LOAN_SELECT, c);
		}
		return null;
	}
	
	/**
	 * Get a loan given an id from the book and an id from the user
	 */
	public Loan getLoan(int idbook, int iduser) {
		String query = LOAN_SELECT
				+ "WHERE id_book = "
				+ idbook + " AND id_user = " + iduser;
		return (Loan)getSingleObject(query, Loan.class);
	}
	

	//Gets all the loans related to a book
	@SuppressWarnings("unchecked")
	public List<Loan> getLoansAccordingBook(int idbook){
		String query = LOAN_SELECT
				+ "WHERE id_book = " + idbook;
		return (List<Loan>)(Object) getList(query, Loan.class);
	}
	

	  //Gets all the loans from a given user

	@SuppressWarnings("unchecked")
	public List<Loan> getLoansAccordingUser(int iduser){
		String query = LOAN_SELECT
				+ "WHERE id_user = " + iduser;
		return (List<Loan>)(Object) getList(query, Loan.class);
	}

	

	 // Gets the number of rest copies of a book

	public int getRestCopies(int idBook){
		String query = "SELECT copies - "
				+ "(SELECT Count(*) FROM loans WHERE id_book = "
				+ idBook + ") AS avl "
				+ "FROM books WHERE id = " + idBook;
		try {
			ResultSet resultSet = databaseConnection.getResult(query);
			if(resultSet.next()) {
				return resultSet.getInt("avl");
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
	}
	

	 // Determine if there are any remaining books

	public boolean ifUserCanBorrow(String username, int idBook) {
		String query = "SELECT Count(*) AS qty FROM loans AS l "
				+ "INNER JOIN users AS u ON u.id = l.id_user WHERE "
				+ "u.username = '" + username + "' AND id_book = " + idBook;
		try {
			ResultSet resultSet = databaseConnection.getResult(query);
			if(resultSet.next()) {
				return resultSet.getInt("qty") == 0;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}

	
	//private methods
	
	/**
	 * Gets a LibraryInformation object according to a query
	 */
	private LibraryInformation getSingleObject(String query, Class<?> c) {
		try {
			ResultSet resultSet = databaseConnection.getResult(query);
			if(resultSet.next()) {
				return parseStorable(resultSet, c);
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}
	

	 // Gets a list of LibraryInformation objects according to a query

	private List<LibraryInformation> getList(String query, Class<?> c) {
		try {
			List<LibraryInformation> list = new ArrayList<>();
			
			ResultSet resultSet = databaseConnection.getResult(query);
			while(resultSet.next()) {
				list.add(parseStorable(resultSet, c));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	 // Gets a user from a result set

	private User parseUser(ResultSet resultSet) throws SQLException{
		try {
			String username = resultSet.getString("username");
			String firstname = resultSet.getString("firstname");
			String surname = resultSet.getString("surname");
			String houseNumber = resultSet.getString("house_number");
			String streetName = resultSet.getString("street_name");
			String email = resultSet.getString("email");
			String postcode = resultSet.getString("postcode");
			Date birthday = simpleDateFormat.parse(resultSet.getString("birthday"));
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			boolean hasPassword = false;
			for(int i = 1; i<=resultSetMetaData.getColumnCount(); i++) {
				if(resultSetMetaData.getColumnName(i).equalsIgnoreCase("password")) {
					hasPassword = true;
					break;
				}
			}
			String password = hasPassword ? resultSet.getString("password") : new String();
			int id = 0;
			try
			{
				id = resultSet.getInt("id_user");
			} catch (SQLException e) {
				id = resultSet.getInt("id");
			}
			return new User(id, username, password, firstname, surname, houseNumber, streetName, postcode, email, birthday);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	 // Gets a loan from a result set

	private Loan parseLoan(ResultSet resultSet) throws SQLException{
		try {
			User user = parseUser(resultSet);
			Book book = parseBook(resultSet);
			Date borrowedDate = simpleDateFormat.parse(resultSet.getString("borrowed_date"));
			Date dueDate = simpleDateFormat.parse(resultSet.getString("due_date"));
			return new Loan(book, user, borrowedDate, dueDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	 // Gets a book from a result set

	private Book parseBook(ResultSet resultSet) throws SQLException{
		try {
			int id = resultSet.getInt("id_book");
			String title = resultSet.getString("title");
			String query = "SELECT * FROM authors WHERE idbook = " + id;
			ResultSet authorRS = databaseConnection.getResult(query);
			List<Author> authors = new ArrayList<>();
			while(authorRS.next()) {
				int idauthor = authorRS.getInt("id");
				String name = authorRS.getString("name");
					String surname = authorRS.getString("surname");
					authors.add(new AuthorInformation(idauthor, name, surname, id));
			}
			int year = resultSet.getInt("year");
			int copies = resultSet.getInt("copies");
			String publisher = resultSet.getString("publisher");
			Date publicationDate = simpleDateFormat.parse(resultSet.getString("publication_date"));
			int availableCopies = getRestCopies(id);
			return new Book(id, title, authors, year, publisher, copies, availableCopies, publicationDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			throw e;
		}
	}
	

	 //Gets a LibraryInformation object from a result set

	private LibraryInformation parseStorable(ResultSet resultSet, Class<?> c) throws SQLException {
		if(c.equals(Book.class)) {
			return parseBook(resultSet);
		} else if(c.equals(User.class)) {
			return parseUser(resultSet);
		} else if(c.equals(Loan.class)) {
			return parseLoan(resultSet);
		} else {
			return null;
		}
	}
		

	//Executes a statement for updating an element

	private int executeUpdateQuery(String query) {
		try {
			return databaseConnection.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
}

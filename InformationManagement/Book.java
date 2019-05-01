package InformationManagement;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

/**
 * Represents a book from the database.
 *@author ChangTan
 */
public class Book implements LibraryInformation {
	
	private int id;
	private String title;
	private List<Author> authors;
	private int year;
	private String publisher;
	private int copies;
	private int availableCopies;
	private Date publicationDate;
	
	//Constructors 
	
	/**
	 * Creates a book with a random id and all the required information.
	 */
	public Book(int id, String title, List<Author> authors, int year, String publisher, int copies, int availableCopies, Date publicationDate) {
		this.id = id;
		if(title == null || title.isEmpty())
			throw new IllegalArgumentException("The title cannot be empty");
		if(authors == null || authors.size() <= 0)
			throw new IllegalArgumentException("There must be an author");
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.publisher = (publisher == null || publisher.isEmpty()) ? "Unknown":publisher;
		this.copies = copies >= 0 ? copies : 0;
		if(availableCopies > this.copies || availableCopies < 0)
			throw new IllegalArgumentException("Incorrect available copies");
		else
			this.availableCopies = availableCopies <= copies ? availableCopies : 0;
		this.publicationDate = publicationDate;
	}
	
	/**
	 * Creates a book with a random id and one available copy.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(int id, String title, List<Author> authors, int year, String publisher, Date publicationDate) {
		this(id, title, authors, year, publisher, 1, 1, publicationDate);
	}
	
	public Book(int id, String title, Author author, int year, String publisher, int copies, int availableCopies, Date publicationDate) 
	{
		this.id = id;
		if(title == null || title.isEmpty())
			throw new IllegalArgumentException("The title cannot be empty");
		if(author == null)
			throw new IllegalArgumentException("There must be an author");
		this.title = title;
		this.authors = new ArrayList<>();
		this.authors.add(author);
		this.year = year;
		this.publisher = (publisher == null || publisher.isEmpty()) ? "Unknown":publisher;
		this.copies = copies >= 0 ? copies : 0;
		if(availableCopies > this.copies || availableCopies < 0)
			throw new IllegalArgumentException("Incorrect available copies");
		else
			this.availableCopies = availableCopies <= copies ? availableCopies : 0;
		this.publicationDate = publicationDate;
	}
	
	/**
	 * Creates a book with a random id and one available copy. 
	 * 
	 * @param title The title of the book. This cannot be null.
	 * @param authors The author or authors of the book. This cannot be null.
	 * @param year The year of the book.
	 * @param publisher The publisher of the book. The default value is 'Unknown'.
	 * @param publicationDate The date of the publication of the book.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(int id, String title, Author author, int year, String publisher, Date publicationDate) {
		this(id, title, author, year, publisher, 1, 1, publicationDate);
	}

	/** Creates a book with a random id and all the required information.
	 * 
	 * @param title The title of the book. This cannot be null.
	 * @param authors The authors of the book. This cannot be null.
	 * @param year The year of the book.
	 * @param publisher The publisher of the book. The default value is 'Unknown'.
	 * @param copies The number of copies of the book. The minimum is 0.
	 * @param availableCopies The number of available copies of the book. It has to be less or equal to the number of copies.
	 * @param publicationDate The date of the publication of the book.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(String title, List<Author> authors, int year, String publisher, int copies, int availableCopies, Date publicationDate) {
		this(generateRandomId(), title, authors, year, publisher, copies, availableCopies, publicationDate);
	}
	
	/**
	 * Creates a book with a random id and one available copy. 
	 * 
	 * @param title The title of the book. This cannot be null.
	 * @param authors The authors of the book. This cannot be null.
	 * @param year The year of the book.
	 * @param publisher The publisher of the book. The default value is 'Unknown'.
	 * @param publicationDate The date of the publication of the book.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(String title, List<Author> authors, int year, String publisher, Date publicationDate) {
		this(title, authors, year, publisher, 1, 1, publicationDate);
	}
	
	/** Creates a book with a random id and all the required information.
	 * 
	 * @param title The title of the book. This cannot be null.
	 * @param author The author of the book. This cannot be null.
	 * @param year The year of the book.
	 * @param publisher The publisher of the book. The default value is 'Unknown'.
	 * @param copies The number of copies of the book. The minimum is 0.
	 * @param availableCopies The number of available copies of the book. It has to be less or equal to the number of copies.
	 * @param publicationDate The date of the publication of the book.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(String title, Author author, int year, String publisher, int copies, int availableCopies, Date publicationDate) 
	{
		this(generateRandomId(), title, author, year, publisher, copies, availableCopies, publicationDate);
	}
	
	/**
	 * Creates a book with a random id and one available copy. 
	 * 
	 * @param title The title of the book. This cannot be null.
	 * @param author The author of the book. This cannot be null.
	 * @param year The year of the book.
	 * @param publisher The publisher of the book. The default value is 'Unknown'.
	 * @param publicationDate The date of the publication of the book.
	 * 
	 * @throws IllegalArgumentException if the information is incorrect.
	 */
	public Book(String title, Author author, int year, String publisher, Date publicationDate) {
		this(title, author, year, publisher, 1, 1, publicationDate);
	}

	//public methods
	
	/**
	 * Gets the title of the book.
	 * @return the title of the book.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the book.
	 * 
	 * @param title The title of the book.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the author or authors of the book.
	 * 
	 * @return The author or authors of the book.
	 */
	public List<Author> getAuthors() {
		return authors;
	}

	
	/**
	 * Gets the year of publication
	 * 
	 * @return The year.
	 */
	public int getYear() {
		return year < 0 ? 0 : year;
	}

	
	/**
	 * Gets the publisher of the book.
	 * 
	 * @return The publisher.
	 */
	public String getPublisher() {
		return publisher;
	}

	
	/**
	 * Gets the number of available copies of the book. This is less or equal to the number of copies.
	 * 
	 * @return The number of available copies.
	 */
	public int getAvailableCopies() {
		return availableCopies;
	}

	
	/**
	 * Gets the date of publication.
	 * 
	 * @return The date of publication.
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}

	
	/**
	 * Gets the randomly generated id. It can goes from 1 to 999,999.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	
	@Override
	public String toString() {
		String text = title + ". ";
		text += getAuthorsShort();
		text += " " + publisher + " " + year + ".";
		return text;
	}
	
	/**
	 * Gets a short representation of the authors including only the author's surname.
	 * @return a representation of authors
	 */
	public String getAuthorsShort() {
		String text = "";
		Iterator<Author> iterator = authors.iterator();
		while(iterator.hasNext())
		{
			Author author = iterator.next();
			text += author.getDescription();
			if(iterator.hasNext())
				text += ", ";
			else if((author instanceof AuthorInformation
					&& (((AuthorInformation) author).getForename() == null
					|| ((AuthorInformation) author).getForename().isEmpty())))
			{
				text += ".";
			}
		}
		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Book) {
			Book bObj = (Book)obj;
			return bObj.getId() == this.getId();
		}
		return false;
	}

	@Override
	public String insertInformation() {
		String statement = "INSERT INTO "
				+ "books (id, title, year, publisher, copies, publication_date) "
				+ "VALUES ('"
				+ id + "', '"
				+ title + "', '"
				+ year + "', '"
				+ publisher + "', '"
				+ copies + "', '"
				+ getFormatedPublicationDate() + "')";
		return statement;
	}

	@Override
	public String updateInformation(String oldId) {
		String statement = "UPDATE books SET "
				+ "title = '" + title + "', "
				+ "year = '" + year + "', "
				+ "publisher = '" + publisher + "', "
				+ "copies = '" + copies + "', "
				+ "publication_date = '" + getFormatedPublicationDate() + "' "
				+ "WHERE id = " + oldId;
		return statement;
	}

	@Override
	public String deleteStatement() {
		String statement = "DELETE FROM books WHERE id = " + id;
		return statement;
	}
	
	/**
	 * Gets the number of copies of the book in the system
	 * @return the number of copies
	 */
	public int getCopies() {
		return this.copies;
	}

	/**
	 * Gets a long representation of the authors, including their full name
	 * @return a long representation of authors
	 */
	public String getAuthorsLong() {
		String text = "";
		Iterator<Author> iterator = authors.iterator();
		while(iterator.hasNext())
		{
			Author author = iterator.next();
			text += author.getLongDescription();
			if(iterator.hasNext())
				text += ", ";
			else
			{
				text += ".";
			}
		}
		return text;
	}
	
	/**
	 * Generates a random id for a book (from 1 to 999999)
	 * @return the randomly generated id
	 */
	public static int generateRandomId() {
		return 1 + new Random().nextInt(999998);
	}

	//private methods
	
	/**
	 * Gets the publication date using a yyyy-mm-dd format
	 * @return
	 */
	private String getFormatedPublicationDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		return simpleDateFormat.format(publicationDate);
	}
	
}

package InformationManagement;

/**
 * Abstract class used to get the representation of the author. The author could be a person or an organization.
 *@author ChangTan
 */
public abstract class Author implements LibraryInformation {
	
	protected int id;
	protected String name;
	protected int idBook;
	
	//Constructors
	
	/**
	 * Constructor for an author
	 * @param id the id of the author
	 * @param name the name of the author
	 */
	public Author(int id, String name, int idBook) {
		this.id = id;
		this.name = name;
		this.idBook = idBook;
	}
	
	/**
	 * Constructor for an author with a generic id
	 * @param name the name of the author
	 */
	public Author(String name, int idBook) {
		this(0, name, idBook);
	}
	
	//public methods
	
	/**
	 * Returns the description of the author that will be displayed on screen.
	 * 
	 * @return the description of the author.
	 */
	public abstract String getDescription();
	
	/**
	 * Returns the full name of the author. The purpose of this method is for searching authors.
	 * 
	 * @return the full name of the author
	 */
	public abstract String getLongDescription();
	
	/**
	 * Gets the id of the loan
	 * @return the id of the loan
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the loan
	 * @param id the id of the loan
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	@Override
	public String deleteStatement() {
		return "DELETE FROM authors WHERE id = " + id;
	}

}

package InformationManagement;


public class AuthorInformation extends Author{

	private String surname;
	
	//Constructors

	public AuthorInformation(String forename, String surname, int idBook) {
		super(forename, idBook);
		setSurname(surname);
	}
	
	//Create an author.

	public AuthorInformation(int id, String forename, String surname, int idBook) {
		super(id, forename, idBook);
		setSurname(surname);
	}
	
	//public methods                                

	public String getForename() {
		return name;
	}

	public void setSurname(String surname) {
		if(surname == null || surname.isEmpty())
			throw new IllegalArgumentException("The author must have a surname");
		else
			this.surname = surname;
	}

	@Override
	public String getDescription() {
		String description = surname;
		if(name != null && !name.isEmpty())
		{
			description += " ";
			String[] forenames = name.split(" ");
			for(String fn : forenames) {
				if(fn != null && !fn.isEmpty()) {
					description += fn.toUpperCase().charAt(0) + ".";
				}
			}
		}
		return description;
	}
	
	@Override
	public String getLongDescription() {
		if(name != null)
		{
			return name + " " + surname;
		}
		else 
		{
			return surname;
		}
	}

	@Override
	public String insertInformation() {
		String statement = "INSERT INTO "
				+ "authors (name, surname, idbook) "
				+ "VALUES ('"
				+ name + "', '"
				+ surname + "', "
				+ idBook + ")";
		return statement;
	}

	@Override
	public String updateInformation(String oldId) {
		if(id != 0) {
			String statement = "UPDATE authors SET "
					+ "name = '" + name + "', "
					+ "surname = '" + surname + "' "
					+ "WHERE id = " + id;
			return statement;
		}
		return null;
	}
}

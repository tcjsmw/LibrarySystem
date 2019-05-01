package InformationManagement;


public interface LibraryInformation {

	//Gets the statement for inserting information

	String insertInformation();

	//Gets the statement for updating information

	String updateInformation(String oldId);

	String deleteStatement();

}
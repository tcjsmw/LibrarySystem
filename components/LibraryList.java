package components;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import InformationManagement.LibraryInformation;
import database.DatabaseOperation;

/**
 * A collection of library items, where they can be added, removed and processed. 
 * 
 * @author ChangTan
 */
public abstract class LibraryList<T extends LibraryInformation> implements Iterable<T> {

	protected DatabaseOperation databaseOperation;
	protected ArrayList<T> list;
	private Class<?> type;
	
	//Constructors
	
	//list downded from database
	@SuppressWarnings("unchecked")
	protected LibraryList(Class<?> type) throws SQLException {
		databaseOperation = new DatabaseOperation();
		this.type = type;
		list = (ArrayList<T>) databaseOperation.getList(type);
	}

	// A new list copy information

	protected LibraryList(List<T> list) {
		this.list = new ArrayList<>(list);
	}
	

	protected LibraryList(LibraryList<T> libraryList) {
		this.list = new ArrayList<>(libraryList.list);
	}
	
	//public methods
	
	/** 
	 * Returns a reference to the ArrayList of the instance.
	 * 
	 * @return an ArrayList of the elements in the LibraryList.
	 */
	public ArrayList<T> getList(){
		return list;
	}
	

	 //return the number

	public int size() {
		return list.size();
	}
	

	public boolean add(T element) {
		if(element != null && !list.contains(element))
		{
			if(databaseOperation.insertInformation(element))
			{
				list.add(element);
				return true;
			}
		}
		return false;
	}
	


	@Override
	public Iterator<T> iterator(){
		return list.iterator();
	}
	

	public T get(int index) {
		return list.get(index);
	}
	
}

package components;

import javax.swing.table.AbstractTableModel;

/**
 * The table model for the table that will display the info in the administrator panel
 * @author ChangTan
 *
 */
public abstract class AdministratorTable extends AbstractTableModel {

	private static final long serialVersionUID = 2629414609692818460L;

	protected LibraryList<?> libraryList;
	private String[] columns;
	
	//Constructors
	

	 // Creates a table for the information

	public AdministratorTable(final String[] columns, LibraryList<?> libraryList) {
		this.libraryList = libraryList;
		this.columns = columns;
	}

	//public methods
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return libraryList.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}

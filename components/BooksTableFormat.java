package components;

import java.text.SimpleDateFormat;
import java.util.Locale;

import InformationManagement.Book;

/**
 * the table format that will display books in the administrator panel
 * @author ChangTan
 */
public class BooksTableFormat extends AdministratorTable {

	private static final long serialVersionUID = -3723104292351270111L;

	private static final String[] BOOK_COLUMNS = {"Title", "Author", "Year", "Publihser", "Publication date"};

	// Creates a table model for the books in the administrator panel

	public BooksTableFormat(BookList bookList) {
		super(BOOK_COLUMNS, bookList);
	}

	@Override
	public Object getValueAt(int row, int col) {
		Book book = ((BookList)libraryList).getList().get(row);
		switch (col) {
		case 0:
			return book.getTitle();
		case 1:
			return book.getAuthorsShort();
		case 2:
			return book.getYear();
		case 3:
			return book.getPublisher();
		case 4:
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
			return simpleDateFormat.format(book.getPublicationDate());
		default:
			return null;
		}
	}

}

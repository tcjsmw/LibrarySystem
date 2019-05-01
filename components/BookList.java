package components;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import InformationManagement.Author;
import InformationManagement.Book;

/**
 * A collection of books
 * @author ChangTan
 */
public class BookList extends LibraryList<Book>{
	
	//Constructors
	

	  //A new BookList with the books in the database
	  //throws SQLException if a connection error with the database occurs

	public BookList() throws SQLException {
		super(Book.class);
	}
	

	 // A new list cloning the elements from the list

	public BookList(List<Book> list) {
		super(list);
	}
	

	 // A new list cloning the elements from the book list

	public BookList(BookList bookList) {
		super(bookList);
	}

	//public methods

	public BookList searchByTitle(String title){
		if(title != null && !title.isEmpty())
			return new BookList((ArrayList<Book>) list.stream()
					.filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
					.collect(Collectors.toList()));
		return null;
	}
	


	public BookList searchByAuthor(String author){
		if(author != null && !author.isEmpty())
		{
			return new BookList((ArrayList<Book>) list.stream()
					.filter(book -> {
						for(Author a : book.getAuthors())
						{
							if(a.getLongDescription().toLowerCase().contains(author.toLowerCase()))
								return true;
						}
						return false;
					})
					.collect(Collectors.toList()));
		}
		return null;
	}
	

	public BookList searchByPublisher(String publisher){
		if(publisher != null && !publisher.isEmpty())
			return new BookList((ArrayList<Book>) list.stream()
					.filter(book -> book.getPublisher().toLowerCase().contains(publisher.toLowerCase()))
					.collect(Collectors.toList()));
		return null;
	}
	

	public BookList searchByYear(int year){
		if(year > 0)
		{
			return new BookList((ArrayList<Book>) list.stream()
					.filter(book -> book.getYear() == year)
					.collect(Collectors.toList()));
		}
		return null;
	}

}

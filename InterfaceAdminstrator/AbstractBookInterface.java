package InterfaceAdminstrator;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import InformationManagement.Author;
import InformationManagement.Book;

/**
 * A dialog for adding or editing books.
 * @author ChangTan
 */
public abstract class AbstractBookInterface extends EditInformation<Book> {

	private static final long serialVersionUID = 601951954358667717L;

	protected JTextField titleTextField;
	protected AuthorsPanel authorsPanel;
	protected JTextField yearTextField;
	protected JTextField publisherTextField;
	protected JTextField copiesTextField;
	protected JTextField publicationDateTextField;
	
	protected int idBook;
	
	private String[] labels;
	
	//Constructors
	
	/**
	 * Creates a dialog for processing a book.
	 */
	public AbstractBookInterface(boolean isEditable, int idBook) {
		super(isEditable);
		this.idBook = idBook;
		labels = new String[] {"Title", "Authors", "Year", "Publisher", "Copies", "Publication date (dd/mm/yyyy)"};
		initializeComponents();
	}
	
	//protected methods
	
	/**
	 * Initializes the dialog with the fields used for processing a book.
	 */
	protected void initializeComponents() {
		
		LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
		
		titleTextField = new JTextField();
		components.put(labels[0], titleTextField);
		authorsPanel = new AuthorsPanel(idBook);
		components.put(labels[1], authorsPanel);
		yearTextField = new JTextField();
		components.put(labels[2], yearTextField);
		publisherTextField = new JTextField();
		components.put(labels[3], publisherTextField);
		copiesTextField = new JTextField();
		components.put(labels[4], copiesTextField);
		publicationDateTextField = new JTextField();
		components.put(labels[5], publicationDateTextField);
		
		for(Map.Entry<String, JComponent> component : components.entrySet()) {
			if(component.getValue() instanceof JTextField)
				component.getValue().setPreferredSize(new Dimension(150,  0));
		}
		
		super.initializeComponents(components);
	}
	
	/**
	 * Gets the book introduced in the dialog
	 * 
	 * @return the book introduced by the user. Null if the book could not be properly created.
	 */
	@Override 
	protected Book getItem() {
		String title = titleTextField.getText().trim();
		if(title.isEmpty() )
		{
			JOptionPane.showMessageDialog(this, "The title is obligatory", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		List<Author> authors = authorsPanel.getAuthors();
		
		if(authors.size() == 0)
		{
			JOptionPane.showMessageDialog(this, "There must be at least one author", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		int year = 0;
		try {
			year = Integer.parseInt(yearTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Not valid year", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String publisher = publisherTextField.getText().trim();
		int copies = 0;
		try
		{
			copies = Integer.parseInt(copiesTextField.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
		Date publicationDate;
		try
		{
			publicationDate = sdf.parse(publicationDateTextField.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "You must use a valid publication date", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		return new Book(idBook, title, authors, year, publisher, copies, copies, publicationDate);
	}
	
	/**
	 * Fills the fields in the dialog with the information of the book
	 */
	@Override
	protected void fillData(Book book)
	{
		titleTextField.setText(book.getTitle());
		authorsPanel.fillData(book.getAuthors());
		yearTextField.setText(book.getYear() + "");
		publisherTextField.setText(book.getPublisher());
		copiesTextField.setText(book.getCopies() + "");
		publicationDateTextField.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(book.getPublicationDate()));
	}
}

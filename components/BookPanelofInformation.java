package components;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import InformationManagement.Book;
import InformationManagement.Loan;

/**
 * A panel displaying the detailed information of a book
 * @author ChangTan
 */
public class BookPanelofInformation extends AbstractPanelofInformation<Book> {

	private static final long serialVersionUID = -5090273945248803594L;

	private JLabel _idLabel;
	private JLabel _titleLabel;
	private JLabel _authorLabel;
	private JLabel _yearLabel;
	private JLabel _publisherLabel;
	private JLabel _copiesLabel;
	private JLabel _availableCopiesLabel;
	private JList<String> _loansList;
	private JLabel _publicationDateLabel; 
	private JScrollPane listScrollPane;
	
	private DefaultListModel<String> listModel;
	
	private SimpleDateFormat publicationDateFormat;
	
	//Constructors
	
	//panel with the details of a book. Until the book is given, this panel remains invisible.

	public BookPanelofInformation() {
		this(false);
	}
	

	// book detail info component with the option of showing or hiding the users loaning them. Until the book is given, this panel remains invisible.

	public BookPanelofInformation(boolean hideUsersLoaning) {
		initialize(hideUsersLoaning);
		setVisible(false);
	}
	

	 // panel with the details of a given book.

	public BookPanelofInformation(Book book) {
		initialize(false);
		changeItem(book);
	}
	
	//protected methods

	@Override
	protected void setInfo(Book book) {
		_idLabel.setText(book.getId() + "");
		_titleLabel.setText(book.getTitle());
		_authorLabel.setText(book.getAuthorsLong());
		_yearLabel.setText(book.getYear() + "");
		_publisherLabel.setText(book.getPublisher());
		_copiesLabel.setText(book.getCopies() + "");
		_availableCopiesLabel.setText(book.getAvailableCopies() + "");
		_publicationDateLabel.setText(publicationDateFormat.format(book.getPublicationDate()));
		
		listModel.clear();
		try {
			LoanList loans = Loans.getLoansByBook(book);
			for(Loan loan : loans) {
				listModel.addElement(loan.getUser().getUsername() + ": " + 
									loan.getUser().getFirstname() + " " + loan.getUser().getSurname());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//private methods

	private void initialize(boolean hide) {
		publicationDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.UK);
		
		_idLabel = new JLabel();
		_titleLabel = new JLabel();
		_authorLabel = new JLabel();
		_yearLabel = new JLabel();
		_publisherLabel = new JLabel();
		_copiesLabel = new JLabel();
		_availableCopiesLabel = new JLabel();
		_loansList = new JList<>();
		_publicationDateLabel = new JLabel();
		
		listModel = new DefaultListModel<>();
		_loansList.setModel(listModel);
		
		listScrollPane = new JScrollPane(_loansList);
		listScrollPane.setAutoscrolls(true);
		listScrollPane.setMaximumSize(new Dimension(listScrollPane.getMaximumSize().width, 200));
		
		LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
		components.put("Book id", _idLabel);
		components.put("Title", _titleLabel);
		components.put("Authors", _authorLabel);
		components.put("Year", _yearLabel);
		components.put("Publisher", _publisherLabel);
		components.put("Copies", _copiesLabel);
		components.put("Available copies", _availableCopiesLabel);
		components.put("Publication date", _publicationDateLabel);
		if(!hide)
			components.put("Users loaning", listScrollPane);
		
		super.initialize(components, true);
	}
}

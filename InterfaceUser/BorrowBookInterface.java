package InterfaceUser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import InformationManagement.Book;
import InformationManagement.Loan;
import InformationManagement.User;
import components.*;
import components.AbstractLibraryInterface;

/**
 * A dialog for borrowing a book. It includes a searching interface where users can look for specific books,
 * view their details and borrow them.
 * @author ChangTan
 */
public class BorrowBookInterface extends AbstractLibraryInterface {

	private static final long serialVersionUID = 4565330739554014094L;

	private JTextField titleTextField;
	private JTextField authorTextField;
	private JTextField publisherTextField;
	private JList<Book> list;
	private BookList bookList;
	private JButton searchButton;
	private JButton borrowButton;
	
	private JScrollPane detailsPanel;
	private BookPanelofInformation bookDetailPanel;
	
	private DefaultListModel<Book> listModel;
	
	private boolean borrowed = false;
	
	private User user;
	private LoanList loanList;
	
	//Constructors
	
	/**
	 * Creates a dialog to borrow books
	 * @param user the user borrowing a book
	 * @param loanList the current loan list of the user
	 */
	public BorrowBookInterface(User user, LoanList loanList) {
		super();
		setTitle("Borrow a book");
		this.user = user;
		this.loanList = loanList;
		try {
			bookList = new BookList();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Unable to connect to the database. Try again later", "Error", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
		initializeComponents();
	}
	
	//public methods

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 400);
	}
	
	/**
	 * Indicates if a book was borrowed.
	 * @return true if a book was borrowed.
	 */
	public boolean wasBorrowed() {
		return borrowed;
	}

	//protected methods
	
	/**
	 * Initializes the components in the dialog. It has three textfields for searching and a list showing the books
	 * matching the list. It also has a info panel for a selected book.
	 */
	protected void initializeComponents() {
		super.initializeComponents();
		
		titleTextField = new JTextField();
		authorTextField = new JTextField();
		publisherTextField = new JTextField();
		
		bookDetailPanel = new BookPanelofInformation(true);
		
		bookDetailPanel.hideButtons();
		
		listModel = new DefaultListModel<>();
		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchButton = new JButton("Search");
		borrowButton = new JButton("Borrow");
		setupComponents();
	}
	
	//private methods
	
	/**
	 * Setups the components
	 */
	private void setupComponents() {
		JPanel dataPanel = getDataPanel();
		dataPanel.setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("Title: ");
		JLabel authorLabel = new JLabel("Author: ");
		JLabel publisherLabel = new JLabel("Publisher: ");
		
		searchButton.setBorder(new EmptyBorder(0, 6, 0, 6));
		searchButton.addActionListener(l -> searchBook());
		
		JPanel filterPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(filterPanel);
		groupLayout.setHorizontalGroup(
					groupLayout.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup()
									.addComponent(titleLabel)
									.addComponent(authorLabel)
									.addComponent(publisherLabel)
								)
						.addGroup(
								groupLayout.createParallelGroup()
									.addComponent(titleTextField)
									.addComponent(authorTextField)
									.addComponent(publisherTextField)
								)
						.addComponent(searchButton)
				);
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
					.addGroup(
								groupLayout.createParallelGroup()
									.addComponent(titleLabel)
									.addComponent(titleTextField)
							)
					.addGroup(
							groupLayout.createParallelGroup()
								.addComponent(authorLabel)
								.addComponent(authorTextField)
						)
					.addGroup(
							groupLayout.createParallelGroup()
								.addComponent(publisherLabel)
								.addComponent(publisherTextField)
								.addComponent(searchButton)
						)
					);
		filterPanel.setLayout(groupLayout);
		
		dataPanel.add(filterPanel, BorderLayout.NORTH);
		
		JScrollPane jScrollPane = new JScrollPane(list);
		dataPanel.add(jScrollPane, BorderLayout.CENTER);
		
		list.addListSelectionListener(l -> showBookInfo());
		
		
		JPanel _auxPanel = new JPanel();
		_auxPanel.add(bookDetailPanel);
		
		detailsPanel = new JScrollPane(_auxPanel);

		JPanel _detailsPanel = new JPanel(new BorderLayout());
		_detailsPanel.setBorder(new EtchedBorder());
		_detailsPanel.add(detailsPanel, BorderLayout.CENTER);
		
		borrowButton.addActionListener(l -> borrow());
		borrowButton.setBorder(new EmptyBorder(6, 6, 6, 6));
		borrowButton.setAlignmentX(RIGHT_ALIGNMENT);
		
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(borrowButton, BorderLayout.EAST);
		_detailsPanel.add(southPanel, BorderLayout.SOUTH);
	
		
		_detailsPanel.setPreferredSize(new Dimension(500, 0));
		_detailsPanel.setMinimumSize(new Dimension(140, 100));
		_detailsPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
		borrowButton.setVisible(false);
		add(_detailsPanel, BorderLayout.EAST);
	}
	
	/**
	 * Performs a search according to the data introduced by the user. If no filters were used, it displays all the books.
	 */
	private void searchBook() {
		BookList searchedBooks = new BookList(bookList);
		String title = titleTextField.getText().trim();
		String author = authorTextField.getText().trim();
		String publisher = publisherTextField.getText().trim();
		
		if(!title.isEmpty())
		{
			searchedBooks = searchedBooks.searchByTitle(title);
		}
		if(!author.isEmpty())
		{
			searchedBooks = searchedBooks.searchByAuthor(author);
		}
		if(!publisher.isEmpty())
		{
			searchedBooks = searchedBooks.searchByPublisher(publisher);
		}
		
		listModel.clear();
		for(Book book : searchedBooks) {
			listModel.addElement(book);
		}
		bookDetailPanel.setVisible(false);
		borrowButton.setVisible(false);
		list.invalidate();
		
	}
	
	/**
	 * Shows the detailed info of a selected book from the list
	 */
	private void showBookInfo() {
		int index = getRowSelected();
		if(index >= 0) {
			Book book = listModel.get(index);
			bookDetailPanel.changeItem(book);
			bookDetailPanel.setVisible(true);
			borrowButton.setVisible(true);
			bookDetailPanel.invalidate();
		}
	}

	/**
	 * Gets the row selected from the list
	 * @return the index of the row selected from the list
	 */
	private int getRowSelected() {
		int index = -1;
		for(int i = 0; i<list.getModel().getSize(); i++) {
			if(list.getSelectionModel().isSelectedIndex(i)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * Borrows a the book if it is available and if the user has not already borrow it.
	 */
	private void borrow() {
		int index = getRowSelected();
		if(index >= 0) {
			Book book = listModel.get(index);
			boolean alreadyOnLoan = false;
			for(Loan loan :loanList) {
				if(loan.getBook().equals(book)) {
					alreadyOnLoan = true;
					break;
				}
			}
			if(!alreadyOnLoan)
			{
				if(book.getAvailableCopies() > 0)
				{
					int result = JOptionPane.showConfirmDialog(this, "Do you want to borrow '" + book.getTitle() + "'?", "Confirm borrow", JOptionPane.YES_NO_CANCEL_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						try {
							Loans.borrowABook(user, book);
							borrowed = true;
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(this, "Unable to borrow this book because a conection problem. Try again later", "Error", JOptionPane.ERROR_MESSAGE);
						}
						this.dispose();
					}
					else if(result == JOptionPane.NO_OPTION) {
						this.dispose();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "There are not available copies for this book.", "Unavailable book", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "You have previously borrowed this book", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

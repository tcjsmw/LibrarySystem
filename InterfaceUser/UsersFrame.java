package InterfaceUser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import InformationManagement.Book;
import InformationManagement.Loan;
import InformationManagement.User;
import components.LoanPanelofInformation;
import components.LoanList;
import components.Loans;

/**
 * The main frame for the use of users.
 * @author ChangTan
 */
public class UsersFrame extends JFrame {

	private static final long serialVersionUID = 8564848197903974229L;

	private JLabel statusLabel;
	private JPanel loansPanel;
	private JScrollPane detailsPanel;
	private User user;
	private LoanPanelofInformation loanInfo;
	private JList<Loan> list;
	private DefaultListModel<Loan> listModel;
	
	private LoanList loanList;
	
	//Constructors

	public UsersFrame(User user) {
		super();
		this.user = user;
		try {
			loanList = Loans.getLoansByUser(user);
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Unable to connect to database. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		setTitle("Library System");
		setPreferredSize(new Dimension(1000, 500));
		initializeComponent();
		
	}
	
	//public methods
	/**
	 * Shows the administrator panel.
	 */
	public void showFrame() {
		pack();
		setVisible(true);
	}
	
	//private methods
	/**
	 * Initialize the components in the frame (menu bar, list, details panel and status bar).
	 */
	private void initializeComponent() {
		makeMenuBar();
		configureStatusLabel();
		configureLoansPanel();
		configureDetailsPanel();
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		container.add(statusLabel, BorderLayout.SOUTH);
		container.add(loansPanel, BorderLayout.CENTER);
		container.add(detailsPanel, BorderLayout.EAST);
	}
		
	// Creates the menu bar.

	private void makeMenuBar() {
		
		JMenu menu;
		JMenuItem item;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//File menu
		menu = new JMenu("Operation");


		item = new JMenuItem("Exit");
		item.addActionListener(l -> exit());
		menu.add(item);
		menuBar.add(menu);
		menu.addSeparator();

		item = new JMenuItem("Borrow a book...");
		item.addActionListener(l -> borrowBooks());
		menu.add(item);
		
		item = new JMenuItem("Return a book...");
		item.addActionListener(l -> returnBook());
		menu.add(item);
		menuBar.add(menu);

	}


	  //Open a dialog to return a book loaned by the user.

	private void returnBook() {
		Book[] books = new Book[loanList.size()];
		for(int i = 0; i<books.length; i++) {
			books[i] = loanList.get(i).getBook();
		}
		Book b = (Book)JOptionPane.showInputDialog(
		                    this,
		                    "Choose a book to return",
		                    "Return a book",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    books, books[0]);

		if (b != null) {
		    try {
				if(Loans.returnABook(user, b))
				{
					setStatus("Book returned: " + b.getTitle());
					update();
				}
				else
				{
					JOptionPane.showMessageDialog(this, "There was an error returning this book. Try again later.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}  catch (HeadlessException | SQLException e) {
				JOptionPane.showMessageDialog(this, "There was an error returning this book. Try again later.", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Opens a dialog to borrow a book
	 */
	private void borrowBooks() {
		BorrowBookInterface bookDialog = new BorrowBookInterface(user, loanList);
		bookDialog.showDialog();
		
		if(bookDialog.wasBorrowed()) {
			update();
			setStatus("Book borrowed");
		}
	}

	
	/**
	 * Closes the application
	 */
	private void exit() {
		this.dispose();
		System.exit(0);
	}
	
	/**
	 * Configures the status bar
	 */
	private void configureStatusLabel() {
		statusLabel = new JLabel(user.getUsername() + " logged succesfully");
		statusLabel.setBorder(new EmptyBorder(6, 6, 6, 6));
	}
	
	/**
	 * Configure the loans panel. It has a list and a button for borrowing books.
	 */
	private void configureLoansPanel() {
		loansPanel = new JPanel();
		loansPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
		loansPanel.setLayout(new BorderLayout());

		listModel = new DefaultListModel<>();
		for(Loan loan : loanList) {
			listModel.addElement(loan);
		}
		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new UsersLoanListRenderer());
		list.getSelectionModel().addListSelectionListener(x -> changeInfo());
		JScrollPane scrollListPane = new JScrollPane(list);
		loansPanel.add(scrollListPane, BorderLayout.CENTER);

	}
	
	/**
	 * Shows the detailed information of a selected loan
	 */
	private void changeInfo() {
		int index = getRowSelected();
		if(index >= 0) {
			Loan loan = loanList.get(index);
			loanInfo.changeItem(loan);
			loanInfo.setVisible(true);
			loanInfo.invalidate();
		}
	}

	/**
	 * Configures the details panel for loans
	 */
	private void configureDetailsPanel() {
		loanInfo = new LoanPanelofInformation();
		loanInfo.hideButtons();
		
		JPanel _detailsPanel = new JPanel();
		_detailsPanel.setBorder(new EtchedBorder());
		_detailsPanel.add(loanInfo);
	
		detailsPanel = new JScrollPane(_detailsPanel);
		detailsPanel.setPreferredSize(new Dimension(300, 0));
		detailsPanel.setMinimumSize(new Dimension(240, 100));
		detailsPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
	}
	
	/**
	 * Gets the row selected from the list
	 * @return the index of the row selected
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
	 * Hides the detail info panel (if no loan is selected)
	 */
	private void hideLoanInfo() {
		loanInfo.setVisible(false);
		loanInfo.invalidate();
	}

	/**
	 * Updates the loans downloading them again from the database.
	 */
	private void update() {
		try {
			loanList = Loans.getLoansByUser(user);
			listModel.clear();
			for(Loan loan : loanList) {
				listModel.addElement(loan);
			}
			
			hideLoanInfo();
			list.invalidate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Unable to connect to database. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Change the status text in the status bar
	 */
	private void setStatus(String status) {
		if(status != null)
			statusLabel.setText(status);
	}
	
}

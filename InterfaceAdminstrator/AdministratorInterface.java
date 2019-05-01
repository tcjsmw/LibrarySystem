package InterfaceAdminstrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import components.*;
import database.DatabaseConnection;
import database.DatabaseOperation;
import InformationManagement.Book;
import InformationManagement.Loan;
import InformationManagement.User;
import components.BookPanelofInformation;

import java.util.List;

/**
 * The main frame for the use of administrators. It includes a filter section, a table and a details panel. 
 * @author ChangTan
 */
public class AdministratorInterface extends JFrame{

	private static final long serialVersionUID = -6306068162801919328L;
	
	private static final int USERS_INDEX = 0;
	private static final int BOOKS_INDEX = 1;
	private static final int LOANS_INDEX = 2;

	private Container mainContainer;
	private JLabel statusLabel;
	private UserList users;
	private UserList usersOrdered;
	private UserList usersFiltered;
	private BookList books;
	private BookList booksOrdered;
	private BookList booksFiltered;
	private LoanList loans;
	private LoanList loansFiltered;
	private LoanList overdueLoans;
	
	private JTabbedPane filtersPane;
	private JSplitPane infoSplitPane;
	private JPanel infoPane;
	private JScrollPane detailPane;
	private JTable table;
	
	private JCheckBox overdueCheckBox;
	
	private int selectedTab = 0;
	
	private UserPanelofInformation userDetailPanel;
	private BookPanelofInformation bookDetailPanel;
	private LoanPanelofInformation loanDetailPanel;
	
	@SuppressWarnings("rawtypes")
	private List<AbstractPanelofInformation> detailPanes;
	
	//Constructors

	/**
	 * Creates the main panel used for administrators. If a problem with the connection to the database occurs when downloading the info, this frame will be closed.
	 */
	public AdministratorInterface() {
		super();
		setTitle("Durham Library (Administrator)");
		setPreferredSize(new Dimension(1000, 500));
		try {
			users = new UserList();
			books = new BookList();
			loans = new LoanList();
			usersOrdered = new UserList(users);
			usersFiltered = new UserList(users);
			booksOrdered = new BookList(books);
			booksFiltered = new BookList(books);
			loansFiltered = new LoanList(loans);
			overdueLoans = loans.searchOverdues(Calendar.getInstance().getTime());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Unable to connect to database. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
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
	 * Initialize the components in the frame (menu bar, filters panel, table, details panel and status bar).
	 */
	private void initializeComponent() {
		makeMenuBar();
		configureFiltersPane();
		configureInfoPane();
		configureDetailPane();
		configureSplitBar();
		configureStatusLabel();
		
		mainContainer = this.getContentPane();
		mainContainer.setLayout(new BorderLayout());
		
		mainContainer.add(filtersPane, BorderLayout.WEST);
		mainContainer.add(infoSplitPane, BorderLayout.CENTER);
		mainContainer.add(statusLabel, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e)
            {
                DatabaseConnection.getInstance().close();
                e.getWindow().dispose();
            }
		});
	}

	//Configuring sections
	//Creating filters section
	/**
	 * Configures the sections with the filters in a tabbed pane (including searching and sorting).
	 */
	private void configureFiltersPane() {
		filtersPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel usersPanel = getUsersFiltersPanel();
		JPanel booksPanel = getBooksFiltersPanel();
		JPanel loansPanel = getLoansFiltersPanel();
		
		filtersPane.addTab("Users", usersPanel);
		filtersPane.addTab("Books", booksPanel);
		filtersPane.addTab("Loans", loansPanel);
		
		filtersPane.addChangeListener(e -> changeTableModel(filtersPane.getSelectedIndex()));
		filtersPane.setPreferredSize(new Dimension(280,0));
	}
	
	/**
	 * Gets a panel with the filters that could be applied to a user.
	 * @return the panel for the user filters.
	 */
	private JPanel getUsersFiltersPanel() {
		//Creating user					
		JPanel usersPanel = new JPanel();
		BoxLayout userBoxLayout = new BoxLayout(usersPanel, BoxLayout.PAGE_AXIS);
		usersPanel.setLayout(userBoxLayout);

		usersPanel.add(createLeftAlignedTextField("Search by:"));
		
		//Creating filters
		
		String[] userFilters = new String[] {"Username", "Firstname", "Lastname", "Email"};
		usersPanel.add(createFilters(userFilters));

		return usersPanel;
	}
	
	/**
	 * Gets a panel with the filters that could be applied to a book.
	 * @return the panel for the book filters.
	 */
	private JPanel getBooksFiltersPanel() {
		//Creating user		
		JPanel booksPanel = new JPanel();
		
		BoxLayout bookBoxLayout = new BoxLayout(booksPanel, BoxLayout.PAGE_AXIS);
		booksPanel.setLayout(bookBoxLayout);
		
		booksPanel.add(createLeftAlignedTextField("Search by:"));
		
		String[] bookFilters = new String[] {"Title", "Author", "Year", "Publisher"};
		booksPanel.add(createFilters(bookFilters));

		return booksPanel;
	}
	
	/**
	 * Gets a panel with the filters that could be applied to a loan.
	 * @return the panel for the loan filters.
	 */
	private JPanel getLoansFiltersPanel() {
		JPanel loansPanel = new JPanel();
		
		BoxLayout loanBoxLayout = new BoxLayout(loansPanel, BoxLayout.PAGE_AXIS);
		loansPanel.setLayout(loanBoxLayout);
		
		loansPanel.add(createLeftAlignedTextField("Search by:"));
		
		String[] filters = new String[] {"Username", "Book id", "Book title"};
		loansPanel.add(createFilters(filters));
		
		JPanel overduePanel = new JPanel(new BorderLayout());
		overdueCheckBox = new JCheckBox("Overdue");
		overdueCheckBox.setAlignmentX(LEFT_ALIGNMENT);
		overdueCheckBox.setBorder(new EmptyBorder(6, 6, 6, 6));
		overdueCheckBox.addActionListener(e -> changeTableModel());
		overduePanel.add(overdueCheckBox);
		overduePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, overdueCheckBox.getPreferredSize().height));
		loansPanel.add(overduePanel);
		
		return loansPanel;
	}
	
	/**
	 * Creates a panel with a label aligned to the left
	 * @param label the label to display
	 * @return the panel created
	 */
	private JPanel createLeftAlignedTextField(String label) {
		JPanel searchPanel = new JPanel(new BorderLayout());
		JLabel searchLabel = new JLabel(label);
		searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		searchLabel.setBorder(new EmptyBorder(6, 6, 6, 6));
		searchPanel.add(searchLabel);
		searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchLabel.getPreferredSize().height));
		return searchPanel;
	}
	
	/**
	 * Creates the filters used for searching information. It will create label - textfield pairs defined by the 
	 * array of filters
	 * @param filters fields to filter the information
	 * @return a panel with the fields created
	 */
	private JPanel createFilters(String[] filters) {
		JPanel filtersCheckboxesPane = new JPanel();
		GroupLayout groupLayout = new GroupLayout(filtersCheckboxesPane);
		filtersCheckboxesPane.setLayout(groupLayout);
		
		ArrayList<JCheckBox> filtersCBs = new ArrayList<>();
		ArrayList<JTextField> filtersTFs = new ArrayList<>();
		
		for(String filter : filters) {
			JCheckBox checkBox = new JCheckBox(filter + ": ");
			checkBox.addActionListener(e -> filterData(filtersCBs, filtersTFs));
			filtersCBs.add(checkBox);
			
			JTextField filterTextField = new JTextField();
			filterTextField.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					filterData(filtersCBs, filtersTFs);
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			filterTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterTextField.getPreferredSize().height));
			filtersTFs.add(filterTextField);
		}
		
		Group horizontalGroup = groupLayout.createSequentialGroup();
	
		Group cbParallelGroup = groupLayout.createParallelGroup();
		for(JCheckBox jCheckBox : filtersCBs) {
			cbParallelGroup.addComponent(jCheckBox);
		}
		Group tfParallelGroup = groupLayout.createParallelGroup();
		for(JTextField jTextField : filtersTFs) {
			tfParallelGroup.addComponent(jTextField);
		}
		horizontalGroup.addGroup(cbParallelGroup).addGroup(tfParallelGroup);				
		groupLayout.setHorizontalGroup(horizontalGroup);
		
		
		Group verticalGroup = groupLayout.createSequentialGroup();
		for(int i = 0; i<filters.length; i++) {
			verticalGroup.addGroup(
					groupLayout.createParallelGroup()
						.addComponent(filtersCBs.get(i))
						.addComponent(filtersTFs.get(i)));
		}
		
		groupLayout.setVerticalGroup(verticalGroup);
		
		filtersCheckboxesPane.setBorder(new EmptyBorder(0, 12, 6, 6));
		return filtersCheckboxesPane;
	}


	//Creating list section
	/**
	 * Configures the panel that will display the table containing the information
	 */
	private void configureInfoPane() {
		
		infoPane = new JPanel(new GridLayout());
		
		UsersTable usersTableModel = new UsersTable(usersOrdered);
		table = new JTable(usersTableModel) {
			private static final long serialVersionUID = -3648511151166921064L;

			@Override
			public TableCellRenderer getCellRenderer (int arg0, int arg1) {
				return new AdministratorTableCellRenderer();
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getSelectionModel().addListSelectionListener(e -> changeDetails());
		JScrollPane infoScrollPane = new JScrollPane(table);
		infoScrollPane.setBackground(Color.WHITE);
		
		infoPane.add(infoScrollPane);
		infoPane.setMinimumSize(new Dimension(100, 100));
		
	}
	
	/**
	 * Configures the split bar containing both the table and the details panel
	 */
	private void configureSplitBar() {
		infoSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		infoSplitPane.setBorder(new EmptyBorder(24, 6, 0, 6));
		
		infoSplitPane.setLeftComponent(infoPane);
		infoSplitPane.setRightComponent(detailPane);
		
		infoSplitPane.setDividerLocation(380 + infoSplitPane.getInsets().left);
		infoSplitPane.setResizeWeight(0.8);
	}
	
	/**
	 * Configures the detail panel creating the three types of AbstractPanelofInformation
	 */
	private void configureDetailPane() {
		userDetailPanel = new UserPanelofInformation();
		bookDetailPanel = new BookPanelofInformation();
		loanDetailPanel = new LoanPanelofInformation();
		
		detailPanes = new ArrayList<>();
		detailPanes.add(userDetailPanel);
		detailPanes.add(bookDetailPanel);
		detailPanes.add(loanDetailPanel);
		for(AbstractPanelofInformation<?> dP : detailPanes) {
			dP.setBorder(new EmptyBorder(6, 6, 6, 6));
		}
		JPanel _detailsPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(_detailsPanel, BoxLayout.PAGE_AXIS);
		_detailsPanel.setLayout(boxLayout);
		_detailsPanel.add(userDetailPanel);
		_detailsPanel.add(bookDetailPanel);
		_detailsPanel.add(loanDetailPanel);
		
		detailPane = new JScrollPane(_detailsPanel);
		detailPane.setPreferredSize(new Dimension(320, 0));
		detailPane.setMinimumSize(new Dimension(240, 100));
		
		userDetailPanel.getEditButton().addActionListener(e -> editUser());
		userDetailPanel.getDeleteButton().addActionListener(e -> deleteUser());
		bookDetailPanel.getEditButton().addActionListener(e -> editBook());
		bookDetailPanel.getDeleteButton().addActionListener(e -> deleteBook());
		loanDetailPanel.getDeleteButton().addActionListener(e -> deleteLoan());
		hideDetailScrolls();		
	}
	
	/**
	 * Configures the status bar
	 */
	private void configureStatusLabel() {
		statusLabel = new JLabel("Data loaded succesfully");
		statusLabel.setBorder(new EmptyBorder(6, 6, 6, 6));
	}

	/**
	 * Creates the menu bar.
	 */
	private void makeMenuBar() {
		
		JMenu menu;
		JMenuItem item;
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		

		
		//User menu
		menu = new JMenu("Users");
		item = new JMenuItem("Add user...");
		item.addActionListener(l -> addUser());
		menu.add(item);
		
		item = new JMenuItem("Edit or delete user");
		item.addActionListener(l -> editUser());
		menu.add(item);

		menuBar.add(menu);
		
		//Books menu
		
		menu = new JMenu("Books");
		item = new JMenuItem("Add book");
		item.addActionListener(l -> addBook());
		menu.add(item);
		
		item = new JMenuItem("Edit or delete books");
		item.addActionListener(l -> editBook());
		menu.add(item);

		menuBar.add(menu);

		//File menu
		menu = new JMenu("Exit");

		item = new JMenuItem("Exit");
		item.addActionListener(l -> exit());
		menu.add(item);
		menuBar.add(menu);


	}
	
	//Menu options

	
	/**
	 * Closes the frame
	 */
	private void exit() {
		System.exit(0);
	}
	
	/**
	 * Opens a dialog for adding a user.
	 */
	private void addUser() {
		AddUserInterface dialog = new AddUserInterface();
		dialog.showDialog();
		
		
		if(dialog.getAdded())
		{
			update();
			setStatus("User added");
		}
	}
	
	/**
	 * Opens a dialog for editing the user selected in the table.
	 */
	private void editUser() {
		if(selectedTab == USERS_INDEX) {
			int index = getRowSelected();
			if(index >= 0) {
				User user = usersFiltered.get(index);
				
				EditUserInterface dialog = new EditUserInterface(user);
				dialog.showDialog();
				
				
				if(dialog.getEdited())
				{
					update();
					setStatus("User " + user.getUsername() + " edited successfuly");
				}
				return;
			}
		}
		
		JOptionPane.showMessageDialog(this, "Please, select a user from the table.", "Error", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Opens a dialog for deleting the user selected in the table.
	 */
	private void deleteUser() {
		if(selectedTab == USERS_INDEX) {
			int index = getRowSelected();
			if(index >= 0) {
				User user = usersFiltered.get(index);
				
				int selection = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user? All loans related will be deleted too", "Warning", JOptionPane.YES_NO_OPTION);
				if(selection == JOptionPane.YES_OPTION) {
					try {
						DatabaseOperation databaseOperation = new DatabaseOperation();
						if(databaseOperation.deleteInformation(user))
						{
							update();
							setStatus("User " + user.getUsername() + " deleted.");
						}
						else
							JOptionPane.showMessageDialog(this, "Unable to delete this user. Try again", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "There was a connection error.", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}	
				}
				
				return;
			}
		}
		
		JOptionPane.showMessageDialog(this, "Please, select a user from the table.", "Error", JOptionPane.INFORMATION_MESSAGE);
	}

	
	/**
	 * Opens a dialog for adding a book.
	 */
	private void addBook() {
		AddBookInterface dialog = new AddBookInterface();
		dialog.showDialog();
		
		
		if(dialog.getAdded())
		{
			update();
			setStatus("Book added");
		}
	}
	
	/**
	 * Opens a dialog for editing the book selected in the table.
	 */
	private void editBook() {
		if(selectedTab == BOOKS_INDEX) {
			int index = getRowSelected();
			if(index >= 0) {
				Book book = booksFiltered.get(index);
				
				EditBookInterface dialog = new EditBookInterface(book);
				dialog.showDialog();
				
				
				if(dialog.getEdited())
				{
					update();
					setStatus("Book " + book.getTitle() + " edited");
				}
				return;
			}
		}
		
		JOptionPane.showMessageDialog(this, "Please, select a book from the table.", "Error", JOptionPane.INFORMATION_MESSAGE);
	
	}

	
	/**
	 * Opens a dialog for deleting the book selected in the table.
	 */
	private void deleteBook() {
		if(selectedTab == BOOKS_INDEX) {
			int index = getRowSelected();
			if(index >= 0) {
				Book book = booksFiltered.get(index);
				
				int selection = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book? All loans related will be deleted too", "Warning", JOptionPane.YES_NO_OPTION);
				if(selection == JOptionPane.YES_OPTION) {
					try {
						DatabaseOperation databaseOperation = new DatabaseOperation();
						if(databaseOperation.deleteInformation(book))
						{
							update();
							setStatus("Book " + book.getTitle() + " deleted");
						}
						else
							JOptionPane.showMessageDialog(this, "Unable to delete this book. Try again", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "There was a connection error.", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}	
				}
				
				return;
			}
		}
		
		JOptionPane.showMessageDialog(this, "Please, select a book from the table.", "Error", JOptionPane.INFORMATION_MESSAGE);
	}
	


	/**
	 * Delete the loan selected in the table.
	 */
	private void deleteLoan() {
		if(selectedTab == LOANS_INDEX) {
			int index = getRowSelected();
			if(index >= 0) {
				Loan loan = loansFiltered.get(index);
				
				int selection = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this loan?", "Warning", JOptionPane.YES_NO_OPTION);
				if(selection == JOptionPane.YES_OPTION) {
					try {
						DatabaseOperation databaseOperation = new DatabaseOperation();
						if(databaseOperation.deleteInformation(loan))
						{
							update();
							setStatus("Loan of book " + loan.getBook().getId() + " deleted from user " + loan.getUser().getUsername());
						}
						else
							JOptionPane.showMessageDialog(this, "Unable to delete this loan. Try again", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "There was a connection error.", "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}	
				}
				
				return;
			}
		}
		
		JOptionPane.showMessageDialog(this, "Please, select a loan from the table.", "Error", JOptionPane.INFORMATION_MESSAGE);
	
	}
	
	/**
	 * Change the status text in the status bar
	 * @param status the status to display
	 */
	private void setStatus(String status) {
		if(status != null)
			statusLabel.setText(status);
	}
	
	/**
	 * Change the details in the detail panel according to the data selected in the table
	 */
	private void changeDetails() {
		int index = getRowSelected();
		if(index >= 0)
		{
			showDetailScrolls();
			if(selectedTab == USERS_INDEX)
			{
				if(userDetailPanel == null) {
					userDetailPanel = new UserPanelofInformation(usersFiltered.get(index));
				}
				else
				{
					userDetailPanel.changeItem(usersFiltered.get(index));
				}
				userDetailPanel.invalidate();
			} else if(selectedTab == BOOKS_INDEX) {
				if(bookDetailPanel == null) {
					bookDetailPanel = new BookPanelofInformation(booksFiltered.get(index));
				}
				else {
					bookDetailPanel.changeItem(booksFiltered.get(index));
				}
				bookDetailPanel.invalidate();
			}
			else if(selectedTab == LOANS_INDEX) {
				Loan loan = overdueCheckBox.isSelected() ? overdueLoans.get(index) : loansFiltered.get(index);
				if(loanDetailPanel == null) {
					loanDetailPanel = new LoanPanelofInformation(loan);
				}
				else {
					loanDetailPanel.changeItem(loan);
				}
				loanDetailPanel.invalidate();
			}
		}
	}
	
	/**
	 * Changes the table model depending on the type of information that it should be displayed
	 */
	private void changeTableModel() {
		if(selectedTab == USERS_INDEX) {
			table.setModel(new UsersTable(usersFiltered));
			table.invalidate();
		}
		else if(selectedTab == BOOKS_INDEX) {
			table.setModel(new BooksTableFormat(booksFiltered));
			table.invalidate();
		}
		else if(selectedTab == LOANS_INDEX) { 
			table.setModel(new LoansTable(overdueCheckBox.isSelected() ? overdueLoans : loansFiltered));
			table.invalidate();
		}
	}
	
	/**
	 * Changes the table model depending on the type of information that it should be displayed
	 * 
	 * @param tab the index of the selected tab
	 */
	private void changeTableModel(int tab) {
		selectedTab = tab;
		hideDetailScrolls();
		changeTableModel();
	}
	
	/**
	 * Hides the scrolls of the detail panel
	 */
	private void hideDetailScrolls() {
		for(AbstractPanelofInformation<?> dPane : detailPanes) {
			dPane.setVisible(false);
		}
		detailPane.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		detailPane.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_NEVER);
	}
	
	/**
	 * Shows the scrolls of the detail panel
	 */
	private void showDetailScrolls() {
		detailPanes.get(selectedTab).setVisible(true);
		detailPane.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		detailPane.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
	}


	/**
	 * Updates the table with the filtered data
	 * @param checkBoxs a list of checkboxes that are used as filters
	 * @param fields the list of fields introduced by the administrator
	 */
	private void filterData(List<JCheckBox> checkBoxs, List<JTextField> fields) {
		switch (selectedTab) {
		case USERS_INDEX:
			usersFiltered = new UserList(usersOrdered);
			if(checkBoxs.get(0).isSelected() && !fields.get(0).getText().isEmpty()) {
				usersFiltered = usersFiltered.searchUsersByUsername(fields.get(0).getText());
			}
			if(checkBoxs.get(1).isSelected() && !fields.get(1).getText().isEmpty()) {
				usersFiltered = usersFiltered.searchUsersByFirstname(fields.get(1).getText());
			}
			if(checkBoxs.get(2).isSelected() && !fields.get(2).getText().isEmpty()) {
				usersFiltered = usersFiltered.searchUsersBySurname(fields.get(2).getText());
			}
			if(checkBoxs.get(3).isSelected() && !fields.get(3).getText().isEmpty()) {
				usersFiltered = usersFiltered.searchUsersByEmail(fields.get(3).getText());
			}
			break;
		case BOOKS_INDEX:
			booksFiltered = new BookList(booksOrdered);
			if(checkBoxs.get(0).isSelected() && !fields.get(0).getText().isEmpty()) {
				booksFiltered = booksFiltered.searchByTitle(fields.get(0).getText());
			}
			if(checkBoxs.get(1).isSelected() && !fields.get(1).getText().isEmpty()) {
				booksFiltered = booksFiltered.searchByAuthor(fields.get(1).getText());
			}
			if(checkBoxs.get(2).isSelected() && !fields.get(2).getText().isEmpty()) {
				try {
				booksFiltered = booksFiltered.searchByYear(Integer.parseInt(fields.get(2).getText()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			if(checkBoxs.get(3).isSelected() && !fields.get(3).getText().isEmpty()) {
				booksFiltered = booksFiltered.searchByPublisher(fields.get(3).getText());
			}
			break;
		case LOANS_INDEX:
			loansFiltered = new LoanList(loans);
			if(checkBoxs.get(0).isSelected() && !fields.get(0).getText().isEmpty()) {
				loansFiltered = loansFiltered.searchByUsername(fields.get(0).getText());
			}
			if(checkBoxs.get(1).isSelected() && !fields.get(1).getText().isEmpty()) {
				try {
					loansFiltered = loansFiltered.searchByBookId(Integer.parseInt(fields.get(1).getText()));
				} catch (NumberFormatException e) {
				}
			}
			if(checkBoxs.get(2).isSelected() && !fields.get(2).getText().isEmpty()) {
				loansFiltered = loansFiltered.searchByBookTitle(fields.get(2).getText());
			}
			overdueLoans = loansFiltered.searchOverdues(Calendar.getInstance().getTime());
			break;
		default:
			break;
		}

		changeTableModel();
	}

	/**
	 * Updates the lists downloading them again from the database.
	 */
	private void update() {
		try {
			users = new UserList();
			books = new BookList();
			loans = new LoanList();
			usersOrdered = new UserList(users);
			usersFiltered = new UserList(usersOrdered);
			booksOrdered = new BookList(books);
			booksFiltered = new BookList(booksOrdered);
			loansFiltered = new LoanList(loans);
			overdueLoans = loans.searchOverdues(Calendar.getInstance().getTime());
			
			hideDetailScrolls();
			changeTableModel();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Unable to connect to database. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Gets the row selected in the table
	 * @return the index of the row selected
	 */
	private int getRowSelected() {
		int index = -1;
		for(int i = 0; i<table.getRowCount(); i++) {
			if(table.getSelectionModel().isSelectedIndex(i)) {
				index = i;
				break;
			}
		}
		return index;
	}

}

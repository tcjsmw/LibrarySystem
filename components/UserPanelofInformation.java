package components;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import InformationManagement.Loan;
import InformationManagement.User;

/**
 * A panel displaying the detailed information of a user
 * 
 * @author ChangTan
 */
public class UserPanelofInformation extends AbstractPanelofInformation<User> {

	private static final long serialVersionUID = 8611883057829654226L;

	private JLabel _userLabel;
	private JLabel _firstnameLabel;
	private JLabel _surnameLabel;
	private JLabel _addressLabel;
	private JLabel _postcodeLabel;
	private JLabel _emailLabel;
	private JLabel _birthdayLabel;
	private JList<String> _loansList;
	private JLabel _overdueLabel; 
	
	DefaultListModel<String> listModel;
	
	private SimpleDateFormat birthdayDateFormat;
	
	//Constructors
	/**
	 * Creates a panel with the details of a user. Until the user is given, this panel remains invisible.
	 */
	public UserPanelofInformation() {
		initialize();
		setVisible(false);
	}
	
	/**
	 * Creates a panel with the details of a given user.
	 * @param user the user to display
	 */
	public UserPanelofInformation(User user) {
		initialize();
		changeItem(user);
	}
	
	//protected methods
	
	/**
	 * Shows the information of the given user
	 * 
	 * @param user the user to show
	 */
	@Override
	protected void setInfo(User user) {
		_userLabel.setText(user.getUsername());
		_firstnameLabel.setText(user.getFirstname());
		_surnameLabel.setText(user.getSurname());
		_addressLabel.setText(user.getHouseNumber() + " " + user.getStreetName());
		_postcodeLabel.setText(user.getPostCode());
		_emailLabel.setText(user.getEmail());
		_birthdayLabel.setText(birthdayDateFormat.format(user.getBirthday()));
		
		listModel.clear();
		try {
			LoanList loans = Loans.getLoansByUser(user.getId());
			int overdue = 0;
			Calendar calendar = Calendar.getInstance();
			Date today = calendar.getTime();
			for(Loan loan : loans) {
				listModel.addElement(loan.getBook().getTitle());
				if(loan.isOverdue(today))
					overdue++;
			}
			
			_overdueLabel.setText(overdue + "");
		} catch (SQLException e) {
			e.printStackTrace();
			_overdueLabel.setText("0");
		}
	}
	
	//private methods
	/**
	 * Initialize the components of the panel
	 */
	private void initialize() {
		birthdayDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.UK);
		
		_userLabel = new JLabel();
		_firstnameLabel = new JLabel();
		_surnameLabel = new JLabel();
		_addressLabel = new JLabel();
		_postcodeLabel = new JLabel();
		_emailLabel = new JLabel();
		_birthdayLabel = new JLabel();
		_loansList = new JList<>();
		_overdueLabel = new JLabel();
		
		listModel = new DefaultListModel<>();
		_loansList.setModel(listModel);
		
		JScrollPane listScrollPane = new JScrollPane(_loansList);
		listScrollPane.setAutoscrolls(true);
		listScrollPane.setMaximumSize(new Dimension(listScrollPane.getMaximumSize().width, 200));
		
		LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
		components.put("User", _userLabel);
		components.put("Firstname", _firstnameLabel);
		components.put("Surname", _surnameLabel);
		components.put("Address", _addressLabel);
		components.put("Postcode", _postcodeLabel);
		components.put("Email", _emailLabel);
		components.put("Birthday", _birthdayLabel);
		components.put("Books on loan", listScrollPane);
		components.put("Overdue books", _overdueLabel);
		
		
		
		super.initialize(components, true);
	}

}

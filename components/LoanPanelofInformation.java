package components;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JLabel;
import InformationManagement.Loan;

/**
 * A panel displaying the detailed information of a loan
 * 
 * @author ChangTan
 */
public class LoanPanelofInformation extends AbstractPanelofInformation<Loan> {

	private static final long serialVersionUID = -4795542132973113406L;

	private JLabel _userLabel;
	private JLabel _firstnameLabel;
	private JLabel _surnameLabel;
	private JLabel _emailLabel;
	private JLabel _bookIdLabel;
	private JLabel _bookTitleLabel;
	private JLabel _borrowedDate;
	private JLabel _dueDate;
	private JLabel _overdueDays;
	
	private SimpleDateFormat dateFormat;
	
	//Constructors
	
	/**
	 * Creates a panel with the details of a loan. Until the loan is given, this panel remains invisible.
	 */
	public LoanPanelofInformation() {
		initialize();
		setVisible(false);
	}
	
	/**
	 * Creates a panel with the details of a given loan.
	 */
	public LoanPanelofInformation(Loan loan) {
		initialize();
		changeItem(loan);
	}
	
	//protected methods
	
	/**
	 * Shows the information of the given loan
	 */
	@Override
	protected void setInfo(Loan loan) {

		_userLabel.setText(loan.getUser().getUsername());
		_firstnameLabel.setText(loan.getUser().getFirstname());
		_surnameLabel.setText(loan.getUser().getSurname());
		_emailLabel.setText(loan.getUser().getEmail());
		_bookIdLabel.setText(loan.getBook().getId() + "");
		_bookTitleLabel.setText(loan.getBook().getTitle());
		_borrowedDate.setText(dateFormat.format(loan.getBorrowDate()));
		_dueDate.setText(dateFormat.format(loan.getDueDate()));
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		if(loan.isOverdue(today)) {
			long difference = today.getTime() - loan.getDueDate().getTime();
			_overdueDays.setText(TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) + "");
		}
		else {
			_overdueDays.setText("0");
		}
		
	}
	
	//private methods
	/**
	 * Initialize the components of the panel
	 */
	private void initialize() {
		dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.UK);
				
		_userLabel = new JLabel();
		_firstnameLabel = new JLabel();
		_surnameLabel = new JLabel();
		_emailLabel = new JLabel();
		_bookIdLabel = new JLabel();
		_bookTitleLabel = new JLabel();
		_borrowedDate = new JLabel();
		_dueDate = new JLabel();
		_overdueDays = new JLabel();
		
		LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
		components.put("User", _userLabel);
		components.put("Firstname", _firstnameLabel);
		components.put("Surname", _surnameLabel);
		components.put("Email", _emailLabel);
		components.put("Book id", _bookIdLabel);
		components.put("Book title", _bookTitleLabel);
		components.put("Borrowed date", _borrowedDate);
		components.put("Due date", _dueDate);
		components.put("Overdue days", _overdueDays);
		
		super.initialize(components, false);
	}

}

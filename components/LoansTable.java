package components;

import java.text.SimpleDateFormat;
import java.util.Locale;

import InformationManagement.Loan;

/**
 * The table format for the table that will display loans in the administrator panel
 * @author ChangTan
 */
public class LoansTable extends AdministratorTable {

	private static final long serialVersionUID = 634071781581909373L;

	private static final String[] LOAN_COLUMNS = {"User", "Book id", "Book title", "Borrowed date", "Due date"};


	//Constructors
	public LoansTable(LoanList loanList) {
		super(LOAN_COLUMNS, loanList);
	}

	@Override
	public Object getValueAt(int row, int col) {
		Loan loan = ((LoanList)libraryList).getList().get(row);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
		switch (col) {
		case 0:
			return loan.getUser().getUsername();
		case 1:
			return loan.getBook().getId();
		case 2:
			return loan.getBook().getTitle();
		case 3:
			return simpleDateFormat.format(loan.getBorrowDate());
		case 4:
			return simpleDateFormat.format(loan.getDueDate());
		default:
			return null;
		}
	}
}

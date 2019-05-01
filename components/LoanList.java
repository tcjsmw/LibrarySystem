package components;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import InformationManagement.Loan;

/**
 * A collection of loans, where they can be added, filtered or removed. 
 * 
 * @author ChangTan
 */
public class LoanList extends LibraryList<Loan> {
	
	//Constructors
	

	public LoanList() throws SQLException {
		super(Loan.class);
	}
	
	//copy the information
	public LoanList(List<Loan> list) {
		super(list);
	}
	

	public LoanList(LoanList loanList) {
		super(loanList);
	}
	
	//public methods
	// Filter the loans whose user's username contains a certain string.

	public LoanList searchByUsername(String username){
		if(username != null && !username.isEmpty())
			return new LoanList((ArrayList<Loan>) list.stream()
					.filter(loan -> loan.getUser().getUsername().toLowerCase().contains(username.toLowerCase()))
					.collect(Collectors.toList()));
		return null;
	}


	 //Filter the loans whose book's title contains a certain string.

	public LoanList searchByBookTitle(String title){
		if(title != null && !title.isEmpty())
		{
			return new LoanList((ArrayList<Loan>) list.stream()
					.filter(loan -> loan.getBook().getTitle().toLowerCase().contains(title.toLowerCase()))
					.collect(Collectors.toList()));
		}
		return null;
	}
	

	 // Filter the loans whose book's id contains a certain number.

	public LoanList searchByBookId(int id){
		if(id > 0)
			return new LoanList((ArrayList<Loan>) list.stream()
					.filter(loan ->String.valueOf(loan.getBook().getId()).contains(String.valueOf(id)))
					.collect(Collectors.toList()));
		return null;
	}
	

	 // Filter the overdue loans.

	public LoanList searchOverdues(Date today){
		return new LoanList((ArrayList<Loan>) list.stream()
				.filter(loan -> loan.isOverdue(today))
				.collect(Collectors.toList()));
	}

}

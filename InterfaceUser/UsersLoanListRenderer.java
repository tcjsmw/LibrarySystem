package InterfaceUser;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import InformationManagement.Loan;

/**
 * Creates a ListCellRenderer for the loans in the list.
 * @author ChangTan
 */
public class UsersLoanListRenderer extends JLabel implements ListCellRenderer<Loan> {

	private static final long serialVersionUID = 3125140644363132646L;

	//Constructors
	/**
	 * Creates a renderer for loans
	 */
	public UsersLoanListRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Loan> list, Loan loan, int index, boolean isSelected,
			boolean cellHasFocus) {
		setText(loan.getForUserRepresentation());
		return this;
	}

}

package InterfaceAdminstrator;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import InformationManagement.Author;
import InformationManagement.AuthorInformation;


	// Dialog for adding authors to a book.
public class AddAuthorInterface extends AbstractLibraryInterface {

	private static final long serialVersionUID = -2258723785272732966L;

	private Author author;
	
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JLabel nameLabel;
	private JLabel surnameLabel;
	
	private int idBook;
	
	//Constructors
	
	//Creates a dialog for adding an author.

	public AddAuthorInterface(int idBook) {
		setTitle("Add author");
		this.idBook = idBook;
		setPreferredSize(new Dimension(300, 225));
		initializeComponents();
	}
	
	//public methods
	public Author getAuthor() {
		return author;
	}
	
	//protected methods
	
	//Initialize the components in the dialog.
	protected void initializeComponents() {
		super.initializeComponents();
		
		JPanel contentPane = getDataPanel();

		nameTextField = new JTextField();
		nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
		surnameTextField = new JTextField();
		surnameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, surnameTextField.getPreferredSize().height));

		setPositiveButtonLabel("Add");
		
		JLabel selectLabel = new JLabel("Please enter the author's Firstname and Surname");
		nameLabel = new JLabel("Firstname");
		surnameLabel = new JLabel("Surname");
		
		BoxLayout boxLayout = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
		contentPane.setLayout(boxLayout);			
		contentPane.add(selectLabel);
		contentPane.add(nameLabel);
		contentPane.add(nameTextField);
		contentPane.add(surnameLabel);
		contentPane.add(surnameTextField);
		
	}
	
	@Override

	protected void positiveButtonAction() {
			String name = nameTextField.getText().trim();
			String surname = surnameTextField.getText().trim();
			if(surname.length() < 2) {
				JOptionPane.showMessageDialog(this, "The author must have a surname", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			author = new AuthorInformation(name, surname, idBook);
		close();
	}

	@Override
	protected void negativeButtonAction() {
		super.close();
	}

	
}

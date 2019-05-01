package InterfaceAdminstrator;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import InformationManagement.User;

//A dialog for adding or editing users.

public abstract class AbstractUserInterface extends EditInformation<User> {

	private static final long serialVersionUID = 447913644645056004L;

	protected JTextField userNameTextField;
	protected JPasswordField passwordField;
	protected JTextField firstnameTextField;
	protected JTextField surnameTextField;
	protected JTextField houseNumberTextField;
	protected JTextField streetNameTextField;
	protected JTextField postcodeTextField;
	protected JTextField emailTextField;
	protected JTextField birthdayTextField;
	
	private String[] labels;
	
	//Constructors
	public AbstractUserInterface(boolean isEditable) {
		super(isEditable);
		labels = new String[] {"Username", "Password", "Firstname", "Surname", "House number", "Street name",
				"Postcode", "Email", "Birthday (dd/mm/yyyy)"};
		
		initializeComponents();
	}
	
	//protected methods
	//initialize
	protected void initializeComponents() {
		
		LinkedHashMap<String, JComponent> components = new LinkedHashMap<>();
		
		userNameTextField = new JTextField();
		components.put(labels[0], userNameTextField);
		passwordField = new JPasswordField();
		components.put(labels[1], passwordField);
		firstnameTextField = new JTextField();
		components.put(labels[2], firstnameTextField);
		surnameTextField = new JTextField();
		components.put(labels[3], surnameTextField);
		houseNumberTextField = new JTextField();
		components.put(labels[4], houseNumberTextField);
		streetNameTextField = new JTextField();
		components.put(labels[5], streetNameTextField);
		postcodeTextField = new JTextField();
		components.put(labels[6], postcodeTextField);
		emailTextField = new JTextField();
		components.put(labels[7], emailTextField);
		birthdayTextField = new JTextField();
		components.put(labels[8], birthdayTextField);
		
		for(Map.Entry<String, JComponent> component : components.entrySet()) {
			component.getValue().setPreferredSize(new Dimension(150,  0));
		}
		
		super.initializeComponents(components);
		
	}
	
	//search the user information got

	@Override
	protected User getItem() {
		String username = userNameTextField.getText().replaceAll("\\s", "");
		userNameTextField.setText(username);
		if(username.isEmpty() || username.length() < 6 )
		{
			JOptionPane.showMessageDialog(this, "The username must have at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String password = new String(passwordField.getPassword());
		if(password.isEmpty() || password.length() < 6 )
		{
			JOptionPane.showMessageDialog(this, "The password must have at least 6 characters", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String firstname = firstnameTextField.getText().trim();
		if(firstname.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must include a name", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String surname = surnameTextField.getText().trim();
		if(surname.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must include a surname", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String houseNumber = houseNumberTextField.getText().trim();
		String streetName = streetNameTextField.getText().trim();
		if(streetName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must include a street name", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String postcode = postcodeTextField.getText().replaceAll("\\s", "").toUpperCase();
		postcodeTextField.setText(postcode);
		if(postcode.length() != 6) {
			JOptionPane.showMessageDialog(this, "You must use a valid postcode (6 characters)", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		String email = emailTextField.getText().replaceAll("\\s", "");
		emailTextField.setText(email);
		if(email.length() <= 13 || !email.contains("@durham.ac.uk")) {
			JOptionPane.showMessageDialog(this, "You must use a valid email", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
		Date birthday;
		try
		{
			birthday = sdf.parse(birthdayTextField.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "You must use a valid birthday", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		return new User(username, password, firstname, surname, houseNumber, streetName, postcode, email, birthday);
	}
	

	 // Fill the information of the user
	@Override
	protected void fillData(User user)
	{
		userNameTextField.setText(user.getUsername());
		firstnameTextField.setText(user.getFirstname());
		surnameTextField.setText(user.getSurname());
		houseNumberTextField.setText(user.getHouseNumber());
		streetNameTextField.setText(user.getStreetName());
		emailTextField.setText(user.getEmail());
		postcodeTextField.setText(user.getPostCode());
		birthdayTextField.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(user.getBirthday()));
		
	}
}

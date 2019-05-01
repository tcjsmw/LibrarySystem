import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DatabaseConnection;
import database.DatabaseOperation;
import InformationManagement.User;
import InterfaceAdminstrator.AdministratorInterface;
import InterfaceUser.UsersFrame;

/**
 * Login window can be used by administrators and users.
 */
public class LoginWindow extends JDialog{

	private static final long serialVersionUID = -1609347867743978281L;

	private JPanel container;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JLabel userLabel;
	private JLabel passwordLabel;
	
	//Constructors
	public LoginWindow() {
		setResizable(false);

		userTextField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Log In");
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		userLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		container = new JPanel();
		
		initializeDialog();
	}
	
	//Public methods
	//show login window
	public void showLogin() {
		
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2 - 100;
		final int y = (screenSize.height - getHeight()) / 2 - 200;
		setLocation(x, y);	
		
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension dimension = super.getPreferredSize();
		dimension.setSize(250, dimension.getHeight());
		return dimension;
	}
	
	//private methods
	

	 // Initializes the components of the login window

	private void initializeDialog() {
		container.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		
		
		JPanel inputPane = new JPanel();
		GroupLayout layout = new GroupLayout(inputPane);
		inputPane.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(userLabel)
						.addComponent(passwordLabel))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(userTextField)
							.addComponent(passwordField))
					);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(userLabel)
						.addComponent(userTextField))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(passwordLabel)
							.addComponent(passwordField))
					);
		
		container.add(inputPane);
		container.add(loginButton);
				
		this.setTitle("Login");
		
		setContentPane(container);
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = userTextField.getText();
				String password = new String(passwordField.getPassword());
				User user;
				try {
					DatabaseOperation databaseOperation = new DatabaseOperation();
					if(databaseOperation.loginAdministrator(username, password))
					{
						AdministratorInterface administratorInterface = new AdministratorInterface();
						administratorInterface.showFrame();
						dispose();
					}
					else if((user = databaseOperation.loginUser(username, password)) != null) {
						UsersFrame usersFrame = new UsersFrame(user);
						usersFrame.showFrame();
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(LoginWindow.this, "Username does not match password", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (SQLException e1) {
					DatabaseConnection.getInstance().close();
					dispose();
					System.exit(0);
				}
			}
		});
	}
}

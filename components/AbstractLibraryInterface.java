package components;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * An abstract class used for all the dialogs in the system.
 * @author ChangTan
 *
 */
public abstract class AbstractLibraryInterface extends JDialog {


	private static final long serialVersionUID = -9098103371181463630L;

	private JPanel dataPanel;
	
	//Constructors
	
	/**
	 * Creates a modal dialog.
	 */
	public AbstractLibraryInterface() {
		setModal(true);
	}
	
	//public methods
	
	/**
	 * Shows the dialog in the center of the screen.
	 */
	public void showDialog() {
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2 - 100;
		final int y = (screenSize.height - getHeight()) / 2 - 200;
		setLocation(x, y);
		
		this.pack();
		this.setVisible(true);
	}
	
	//protected methods
	
	/**
	 * Initializes the components on the dialog. The dialog has a BorderLayout and a main panel is in the center.
	 */
	protected void initializeComponents() {
		dataPanel = new JPanel();
		dataPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		setupComponents();
	}
	
	/**
	 * Gets a reference to the data panel at the center of the dialog.
	 */
	protected JPanel getDataPanel() {
		return this.dataPanel;
	}
	
	/**
	 * Closes the dialog
	 */
	protected void close() {
		this.dispose();
	}
	
	//private methods
	
	/**
	 * Setup the dataPanel
	 */
	private void setupComponents() {
		setLayout(new BorderLayout());
		add(dataPanel, BorderLayout.CENTER);
	}	
	
}

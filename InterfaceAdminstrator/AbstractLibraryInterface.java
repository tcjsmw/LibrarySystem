package InterfaceAdminstrator;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * Extension of the AbstractLibraryInterface with positive and negative buttons.
 * @author ChangTan
 */
public abstract class AbstractLibraryInterface extends components.AbstractLibraryInterface {

	private static final long serialVersionUID = 1387780949962244610L;

	private JButton positiveButton;
	private JButton negativeButton;
	private JPanel buttonsPanel;
	
	//protected methods

	@Override
	protected void initializeComponents() {
		super.initializeComponents();
		positiveButton = new JButton("Accept");
		positiveButton.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		positiveButton.addActionListener(e -> positiveButtonAction());
		negativeButton = new JButton("Cancel");
		negativeButton.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		negativeButton.addActionListener(e -> negativeButtonAction());
		
		setupComponents();
	}
	
	/**
	 * Sets the positive button label.
	 */
	protected void setPositiveButtonLabel(String label) {
		if(positiveButton != null) {
			positiveButton.setText(label);
		}
	}


	protected abstract void positiveButtonAction();

	protected abstract void negativeButtonAction();
	
	//private methods

	private void setupComponents()
	{
		buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(positiveButton);
		buttonsPanel.add(negativeButton);
		buttonsPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		add(buttonsPanel, BorderLayout.SOUTH);
	}

}

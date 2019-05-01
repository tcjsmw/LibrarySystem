package components;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import InformationManagement.LibraryInformation;

/**
 * Abstract class that represents a panel displaying detailed info of a LibraryInformation item.
 * @author ChangTan
 */
public abstract class AbstractPanelofInformation<T extends LibraryInformation> extends JPanel {

	private static final long serialVersionUID = 96329072830828371L;

	private Font labelInfoFont;
	private Font detailFont;
	
	private JButton editButton;
	private JButton deleteButton;
	
	protected T libraryInformation;
	
	protected LinkedHashMap<JLabel, JComponent> details;
	
	//public methods

	public void changeItem(T information) {
		this.libraryInformation = information;
		setInfo(information);
	}
	

	// button for editing the information

	public JButton getEditButton() {
		return editButton;
	}


	 // button for deleting the information

	public JButton getDeleteButton() {
		return deleteButton;
	}

	//no action

	public void hideButtons() {
		editButton.setVisible(false);
		deleteButton.setVisible(false);
	}
	
	
	//protected methods
	
	// Initialize the components
	protected void initialize(LinkedHashMap<String, JComponent> components, boolean isEditable) {
		setLayout(new BorderLayout());
		
		labelInfoFont = new Font("Dialog", Font.BOLD, 14);
		detailFont = new Font("Dialog", Font.PLAIN, 12);
		
		details = new LinkedHashMap<>();
		
		for(Map.Entry<String, JComponent> component : components.entrySet()) {
			JLabel label = new JLabel(component.getKey() + ": ");
			label.setFont(labelInfoFont);
			details.put(label, component.getValue());
			details.get(label).setFont(detailFont);
		}		
		
		deleteButton = new JButton("Delete");
		deleteButton.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		editButton = new JButton("Edit");
		editButton.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		editButton.setVisible(isEditable);
		
		JPanel detailPanel = new JPanel();
		detailPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		GroupLayout groupLayout = new GroupLayout(detailPanel);
		Group horizontalGroup = groupLayout.createSequentialGroup();
		
		Group labelHorizontalGroup = groupLayout.createParallelGroup();
		for(JLabel jLabel : details.keySet()) {
			labelHorizontalGroup.addComponent(jLabel);
		}
		Group detailHorizontalGroup = groupLayout.createParallelGroup();
		for(JComponent component : details.values()) {
			detailHorizontalGroup.addComponent(component);
		}
		horizontalGroup.addGroup(labelHorizontalGroup).addGroup(detailHorizontalGroup);
		
		groupLayout.setHorizontalGroup(horizontalGroup);	
		
		Group verticalGroup = groupLayout.createSequentialGroup();
		
		for(Map.Entry<JLabel, JComponent> component : details.entrySet()) {
			verticalGroup.addGroup(groupLayout.createParallelGroup()
						.addComponent(component.getKey())
						.addComponent(component.getValue())
					);
		}
		
		groupLayout.setVerticalGroup(verticalGroup);
		
		detailPanel.setLayout(groupLayout);
		
		this.add(detailPanel, BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.add(editButton);
		buttonsPanel.add(deleteButton);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		JLabel title = new JLabel("Details");
		title.setFont(new Font("Dialog", Font.PLAIN, 18));
		title.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(title, BorderLayout.NORTH);
	}
	
	/**
	 * Fills the panel with the information
	 */
	protected abstract void setInfo(T information);
}

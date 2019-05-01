package InterfaceAdminstrator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import InformationManagement.LibraryInformation;


  // A dialog for adding or editing elements on the database.

public abstract class EditInformation<T extends LibraryInformation> extends AbstractLibraryInterface {

	private static final long serialVersionUID = 8916929106258906317L;

	private boolean isEditable;
	
	private LinkedHashMap<JLabel, JComponent> components;
	
	//Constructors

	/**
	 * Initializes the dialog.
	 */
	public EditInformation(boolean isEditable) {
		super();
		this.isEditable = isEditable;
		this.components = new LinkedHashMap<>();
	}
	
	//protected methods
	
	/**
	 * Initializes the components on the dialog in a list of Label - Component pairs
	 */
	protected void initializeComponents(LinkedHashMap<String, JComponent> components) {
		super.initializeComponents();
		for(Map.Entry<String, JComponent> component : components.entrySet()) {
			JLabel label = new JLabel(component.getKey() + ": ");
			this.components.put(label, component.getValue());
		}
		setPositiveButtonLabel(isEditable ? "Edit" : "Add");
		setupComponents();
	}
	
	/**
	 * Perform an action with the generated item when the positive button is clicked
	 */
	protected abstract void performAction(T item);
	
	/**
	 * Gets the item that the user introduced in the dialog
	 * @return the item processed with the information introuced by the user
	 */
	protected abstract T getItem();
	
	/**
	 * Fills the fields of the dialog with an specified item
	 * @param item the item to display
	 */
	protected abstract void fillData(T item);
	
	protected void positiveButtonAction() {
		performAction(getItem());
	}
	
	protected void negativeButtonAction() {
		super.close();
	}
	
	//private methods
	
	/**
	 * Setups the components in the dialog. 
	 */
	private void setupComponents() {
		
		JPanel fieldsPanel = super.getDataPanel();

		GroupLayout groupLayout = new GroupLayout(fieldsPanel);
		
		Group labelsHorizontalGroup = groupLayout.createParallelGroup();
		Group componentsHorizontalGroup = groupLayout.createParallelGroup();
		
		Group verticalGroup = groupLayout.createSequentialGroup();
		
		for(Map.Entry<JLabel, JComponent> component : components.entrySet()) {
			labelsHorizontalGroup.addComponent(component.getKey());
			componentsHorizontalGroup.addComponent(component.getValue());
			
			verticalGroup.addGroup(groupLayout.createParallelGroup()
					.addComponent(component.getKey())
					.addComponent(component.getValue()));
		}
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
									.addGroup(labelsHorizontalGroup)
									.addGroup(componentsHorizontalGroup));
		
		groupLayout.setVerticalGroup(verticalGroup);
		fieldsPanel.setLayout(groupLayout);
	}
}

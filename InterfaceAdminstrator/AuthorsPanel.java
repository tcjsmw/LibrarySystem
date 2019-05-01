package InterfaceAdminstrator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import InformationManagement.Author;

/**
 * A panel where authors can be added, deleted.
 * @author ChangTan
 */
public class AuthorsPanel extends JPanel {

	private static final long serialVersionUID = 6841634231627421734L;

	private JButton addButton;
	private JButton deleteButton;
	private JButton upButton;
	private JButton downButton;
	private JList<String> jlist;
	
	DefaultListModel<String> listModel;
	
	private ArrayList<Author> authors;
	
	private int idBook;
	
	//Constructors
	
	/**
	 * Creates the authors panel for an specific book
	 * @param idBook the id of the book
	 */
	public AuthorsPanel(int idBook) {
		authors = new ArrayList<>();
		this.idBook = idBook;
		initializeComponents();
	}

	//public methods
	/**
	 * Gets the list of authors added to the panel. Never null.
	 * @return a list of authors.
	 */
	public ArrayList<Author> getAuthors(){
		return authors;
	}

	/**
	 * Fills the list with previous added authors (i.e., if a book is being edited).
	 * @param authors the list of authors of the book.
	 */
	public void fillData(List<Author> authors) {
		for(Author author : authors) {
			this.authors.add(author);
			listModel.addElement(author.getLongDescription());
		}
		invalidate();
	}

	//private methods
	
	/**
	 * Initialize the components of the Panel. It includes a list and four buttons to add, remove, move up and move down the authors.
	 */
	private void initializeComponents() {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(6, 0, 6, 6));
		
		listModel = new DefaultListModel<>();
		jlist = new JList<>();
		jlist.setPreferredSize(new Dimension(150, 75));
		jlist.setModel(listModel);
		
		JScrollPane scrollPane = new JScrollPane(jlist);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS);
		buttonsPanel.setLayout(boxLayout);
		addButton = new JButton("Add");
		addButton.setFont(new Font("Dialog", Font.BOLD, 20));
		addButton.setBorder(null);
		addButton.setFocusPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setForeground(new Color(205, 6, 8));
		addButton.addActionListener(e -> addAuthor());
		addButton.setAlignmentX(CENTER_ALIGNMENT);		
		
		deleteButton = new JButton("Delete");
		deleteButton.setBorder(null);
		deleteButton.setFont(new Font("Dialog", Font.BOLD, 20));
		deleteButton.setFocusPainted(false);
		deleteButton.setContentAreaFilled(false);
		deleteButton.setForeground(new Color(205, 20, 50));
		deleteButton.setAlignmentX(CENTER_ALIGNMENT);
		deleteButton.addActionListener(l -> deleteSelectedAuthor());
		
		upButton = new JButton();
		upButton.setBorderPainted(false);
		upButton.setFocusPainted(false);
		upButton.setContentAreaFilled(false);
		upButton.setPreferredSize(new Dimension(24, 24));
		upButton.addActionListener(l -> upElement());
		upButton.setAlignmentX(CENTER_ALIGNMENT);
		
		downButton = new JButton();
		downButton.setPreferredSize(new Dimension(24, 24));
		downButton.setBorderPainted(false);
		downButton.setFocusPainted(false);
		downButton.setContentAreaFilled(false);
		downButton.setAlignmentX(CENTER_ALIGNMENT);
		downButton.addActionListener(l -> downElement());

		
		buttonsPanel.setBorder(new EmptyBorder(0, 6, 6, 3));
		buttonsPanel.add(addButton);
		buttonsPanel.add(deleteButton);
		buttonsPanel.add(upButton);
		buttonsPanel.add(downButton);
		
		add(buttonsPanel, BorderLayout.EAST);
		invalidate();
	}
	
	/**
	 * Opens a dialog for adding an author to the list
	 */
	private void addAuthor() {
		AddAuthorInterface dialog = new AddAuthorInterface(idBook);
		dialog.showDialog();
		
		Author author = dialog.getAuthor();
		if(author != null) {
			authors.add(author);
			listModel.addElement(author.getLongDescription());
			invalidate();
		}
	}
	
	/**
	 * Removes an author selected on the list.
	 */
	private void deleteSelectedAuthor() {
		int index = getRowSelected();
		if(index >= 0) {
			authors.remove(index);
			listModel.removeElementAt(index);
			invalidate();
		}
	}	
	
	/**
	 * Gets the row selected in the list
	 * @return the index of the author selected on the list
	 */
	private int getRowSelected() {
		int index = -1;
		for(int i = 0; i<listModel.getSize(); i++) {
			if(jlist.isSelectedIndex(i)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * Moves up a selected author
	 */
	private void upElement() {
		int index = getRowSelected();
		if(index > 0 && listModel.size() > 1) {

			String a = listModel.getElementAt(index);
			listModel.setElementAt(listModel.getElementAt(index - 1), index);
			listModel.setElementAt(a, index - 1);
			
			Collections.swap(authors, index, index - 1);
			
			jlist.setSelectedIndex(index - 1);
			
		}
		invalidate();
	}
	
	/**
	 * Moves down a selected author
	 */
	private void downElement() {
		int index = getRowSelected();
		if(index != -1 && index < listModel.size() - 1) {

			String a = listModel.getElementAt(index);
			listModel.setElementAt(listModel.getElementAt(index + 1), index);
			listModel.setElementAt(a, index + 1);
			
			Collections.swap(authors, index, index + 1);
			
			jlist.setSelectedIndex(index + 1);
			
		}
		invalidate();
	}

}

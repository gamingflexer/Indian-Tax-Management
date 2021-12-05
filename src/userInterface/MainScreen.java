package userInterface;


// Java Swing used 
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataManagement.PeopleManager;
import dataManagement.Person;
import javax.swing.JList;
import javax.swing.JOptionPane;

// Action lisner 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataExport.OutputManager;
import dataInput.InputManager;
import dataInput.XmlParser;

import javax.swing.JButton;
import javax.swing.JFileChooser;

// Main Class - Run here

public class MainScreen extends GridBagBasedScreen {

	PeopleManager peopleManager;
	OutputManager outputManager;
	
	private JFrame frame;
	private JLabel mainLabelText;
	private JScrollPane scrollPane;
	private JList list;
	private PersonListModel model;
	private JButton buttonImportPerson;
	private JButton buttonExport;
	private JButton buttonCreatePerson;
	private JButton buttonDeletePerson;
	private JButton buttonClose;
	private String[] personTypes = { "slab_3", "slab_4", "slab_2", "slab_1" };
	private String[] exportTypes = { "Full Data File", "Summary Log" };
	XmlParser inputFileParser;
	String filename = "";
	JFileChooser fileChooser;
	Person selectedObject;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainScreen() {
		initialize();
	}

	private void initialize() {
		
		outputManager = new OutputManager();
		
		frame = new JFrame("Tax-Income Calculator");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600,400));
		
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout( new GridBagLayout() );
		
		frame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				frame.repaint();
				frame.revalidate();
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				
			}
		});
		
		mainLabelText = new JLabel("List of People and Tax Data:");
		mainLabelText.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		peopleManager = new PeopleManager();

		list = new JList();
		list.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		model = new PersonListModel();
		list.setModel(model);
		model.addAll(peopleManager.getPersonList());

		scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(400,400));
		
		// Main Screen Layout Configuration:
		addGridItem(panel, mainLabelText, 0, 0, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, 0.5f);
		addGridItem(panel, scrollPane, 0, 1, 5, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0f);
		
		buttonCreatePerson = new JButton("New");
		buttonDeletePerson = new JButton("Delete");
		buttonImportPerson = new JButton("Import");
		buttonExport = new JButton("Export");
		buttonClose = new JButton("Close");
		
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ( e.getSource() == buttonCreatePerson ) {
					
					Object category = (String)JOptionPane.showInputDialog(frame, "Choose the type of Tax-Payer:", "Create a new Person", JOptionPane.PLAIN_MESSAGE, null, personTypes, personTypes[0]);					
					
					// Finds the index of the selected category:
					int categoryInteger = 0;
					while (personTypes[categoryInteger] != category.toString() && categoryInteger < personTypes.length){
						categoryInteger++;
					}

					Person newPerson = peopleManager.createNewPerson(categoryInteger+1);
					PersonCard createPerson = new PersonCard(newPerson);
					model.addElement(newPerson);
					
				} else if ( e.getSource() == buttonDeletePerson ) {
										
					if (list.getSelectedIndex() >= 0) {
						model.removeItem(list.getSelectedIndex());
					}
					
				} else if ( e.getSource() == buttonImportPerson ) {
					
					showFileImportDialog();
					
				} else if ( e.getSource() == buttonExport ) {
					
					Object typeOfExport = (String)JOptionPane.showInputDialog(frame, "Choose the type of Export:",
							"Export a Person as File", JOptionPane.PLAIN_MESSAGE, null, exportTypes, exportTypes[0]);
					
					if ( typeOfExport.equals("Full Data File") ){
						savePersonAsType("Full");
					} else if ( typeOfExport.equals("Summary Log") ) {
						savePersonAsType("Log");
					}
					
				} else if ( e.getSource() == buttonClose ) {
					frame.dispose();
					System.exit(0);
				}
			}
		};
		
		buttonCreatePerson.addActionListener(buttonListener);
		buttonDeletePerson.addActionListener(buttonListener);
		buttonImportPerson.addActionListener(buttonListener);
		buttonExport.addActionListener(buttonListener);
		buttonClose.addActionListener(buttonListener);

		addGridItem(panel, buttonCreatePerson, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonDeletePerson, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonImportPerson, 2, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonExport, 3, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);
		addGridItem(panel, buttonClose, 4, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0f);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2) {
		          int index = theList.locationToIndex(mouseEvent.getPoint());
		          if (index >= 0 && index < theList.getModel().getSize()) {
		            selectedObject = ((PersonListModel) theList.getModel()).getPersonAt(index);
		            if (selectedObject != null) {
		            	EventQueue.invokeLater(new Runnable(){
		            		public void run(){
		            		new PersonCard(selectedObject).setVisible(true);
		            		}
		            	});
		            }
		          }
		        }
		      }
		};
		    
		list.addMouseListener(mouseListener);
	}
	
	private void savePersonAsType(String type) {
		
		if (list.getSelectedIndex() >= 0) {
			
			Person personObject = model.getPersonAt(list.getSelectedIndex());
			showSaveDialog(personObject, type);
		}
	}
	
	protected void showSaveDialog(Person personObject, String saveType) {
		JFileChooser fileChooser = new JFileChooser();
		
		if (saveType.equals("Full")){
			fileChooser.setSelectedFile(new File(outputManager.getPersonSuggestedFilename(personObject, "xml")));
		} else if (saveType.equals("Log")) {
			fileChooser.setSelectedFile(new File(outputManager.getPersonSuggestedFilename(personObject, "log")));
		}
		
		int returnVal = fileChooser.showSaveDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			
			if ( saveType.equals("Full") ) {
				saveFullFile(personObject, fileChooser.getSelectedFile());
			} else if ( saveType.equals("Log") ) {
				saveLogFile(personObject, fileChooser.getSelectedFile());
			}
		}
	}
	
	protected void saveFullFile(Person personObject, File selectedFile) {
			
			outputManager.savePersonToFile(personObject, selectedFile);
			JOptionPane.showMessageDialog(frame, "The data file was saved to disk.");
	}
	
	protected void saveLogFile(Person personObject, File selectedFile) {

			outputManager.savePersonToLogFile(personObject, selectedFile);
			JOptionPane.showMessageDialog(frame, "The log file was saved to disk.");
	}
	
	protected void showFileImportDialog() {
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and TXT files", "xml", "txt");
		fileChooser.setFileFilter(filter);
		int returnVal = fileChooser.showOpenDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
						
			InputManager inputManager = new InputManager();
			Person importedPerson = inputManager.importPersonFromFile(fileChooser.getSelectedFile());
			model.addElement(importedPerson);		
		}
	}
}
package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import dataManagement.Person;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import dataExport.OutputManager;

public class PersonCard extends GridBagBasedScreen implements ActionListener {
	
	OutputManager outputManager;
	private Person person;
	
	private JButton buttonSaveChanges;
	private JButton buttonClose;
	private JButton buttonAddReceipt;
	private JButton buttonDeleteReceipt;
	private JButton buttonShowPieChart;
	private JButton buttonShowBarChart;
	private JTextArea textId;
	private JTextArea textAreaName;
	private JTextArea textAreaSurname;
	private JTextArea textIncome;
	private JTextArea textTaxAfterReceipts;
	private JLabel personTypeLabel;
	private JLabel fileSaveStateLabel;
	
	private ReceiptsPanel receiptsPanel;
	
	public PersonCard(Person person) {
		
		outputManager = new OutputManager();
		this.person = person;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					showCard();
					SwingUtilities.getRootPane(buttonSaveChanges).setDefaultButton(buttonSaveChanges);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showCard() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150, 150, 750, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(750,400));
		this.setTitle("Indian-Tax-Income Calculator");
		
		this.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				// Do nothing (yet).
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				repaint();
				revalidate();
			}
		});
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		// Get text-field Strings from the Person Object:
		textId = new JTextArea(person.getIdentifyingNumber().toString());
		textAreaName = new JTextArea(person.getFirstName());
		textAreaSurname = new JTextArea(person.getLastName());
		textIncome = new JTextArea(person.getIncome().toString());
		textTaxAfterReceipts = new JTextArea(person.calculateFinalTax().toString());
		personTypeLabel = new JLabel("Category: "+ person.getPersonCategory());
		personTypeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		fileSaveStateLabel = new JLabel("");
		
		// Buttons Initialization:
		buttonSaveChanges = new JButton("Save");
		buttonClose = new JButton("Close");
		buttonAddReceipt = new JButton("Add");
		buttonDeleteReceipt = new JButton("Delete");
		buttonShowPieChart = new JButton("");
		buttonShowBarChart = new JButton("");
		
		buttonSaveChanges.addActionListener(this);
		buttonClose.addActionListener(this);
		buttonAddReceipt.addActionListener(this);
		buttonDeleteReceipt.addActionListener(this);
		buttonShowPieChart.addActionListener(this);
		buttonShowBarChart.addActionListener(this);
		
		// Person placeholder image initialization:
		BufferedImage personPicture = null;
		
		try {
			personPicture = ImageIO.read(getClass().getResourceAsStream("/res/icon-user-small.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel picLabel = new JLabel(new ImageIcon(personPicture));
		picLabel.setMaximumSize(new Dimension(200,200));
		
		// Set up the Grid Bag Layout:
		new GridBagConstraints();
		
		// Put a "title" wrapper around the JTextAreas:
		JPanel wrapperName = new JPanel(new BorderLayout());
		wrapperName.setBorder(new TitledBorder("First Name:"));
		wrapperName.add(textAreaName);
		
		JPanel wrapperSurname = new JPanel(new BorderLayout());
		wrapperSurname.setBorder(new TitledBorder("Last Name:"));
		wrapperSurname.add(textAreaSurname);
		
		JPanel wrapperIncome = new JPanel(new BorderLayout());
		wrapperIncome.setBorder(new TitledBorder("Income:"));
		wrapperIncome.add(textIncome);
		
		JPanel wrapperId = new JPanel(new BorderLayout());
		wrapperId.setBorder(new TitledBorder("Id:"));
		wrapperId.add(textId);
		
		JPanel wrapperTax = new JPanel(new BorderLayout());
		wrapperTax.setBorder(new TitledBorder("Calculated Tax:"));
		wrapperTax.add(textTaxAfterReceipts);
		
		receiptsPanel = new ReceiptsPanel(person.getReceiptsList());
		
		// Add the receipts FlowLayout inside the Titled border Panel:
		JScrollPane scrollReceiptsPanel = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollReceiptsPanel.setBorder(new TitledBorder("Receipts:"));
		scrollReceiptsPanel.setMinimumSize(new Dimension(100, 120));
		scrollReceiptsPanel.setViewportView(receiptsPanel);
		
		addGridItem(panel, personTypeLabel, 0, 0, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, fileSaveStateLabel, 5, 0, 1, 1, GridBagConstraints.CENTER);
		
		addGridItem(panel, picLabel, 0, 1, 2, 4, GridBagConstraints.CENTER);
		addGridItem(panel, wrapperName, 2, 1, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, wrapperSurname, 2, 2, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, wrapperIncome, 2, 3, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, wrapperId, 2, 4, 2, 1, GridBagConstraints.CENTER);
		
		addGridItem(panel, wrapperTax, 4, 1, 2, 1, GridBagConstraints.CENTER);
		addGridItem(panel, scrollReceiptsPanel, 4, 2, 2, 2, GridBagConstraints.CENTER);
		addGridItem(panel, buttonAddReceipt, 4, 4, 1, 1, GridBagConstraints.PAGE_START);
		addGridItem(panel, buttonDeleteReceipt, 5, 4, 1, 1, GridBagConstraints.PAGE_START);
				
		addGridItem(panel, buttonSaveChanges, 0, 5, 1, 1, GridBagConstraints.CENTER);
		addGridItem(panel, buttonClose, 1, 5, 1, 1, GridBagConstraints.CENTER);
		

				
		this.add(panel);
		this.pack();
		this.setVisible(true);
		
		textId.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textAreaName.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textAreaSurname.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textIncome.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		textTaxAfterReceipts.setEditable(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource().equals(buttonSaveChanges) ) {
			
			if ( isNumeric(textIncome.getText()) && isNumeric(textId.getText()) ) {
				updatePersonData();
				outputManager.updatePersonFile(person);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error: Tax-Payer Id and Income should be number values.");
			}
			
		} else if ( e.getSource().equals(buttonClose) ) {
			
			this.dispose();
			
		} else if ( e.getSource().equals(buttonAddReceipt) ) {
			
		} else if ( e.getSource().equals(buttonDeleteReceipt) ) {
			
			if ( receiptsPanel.isAnyListCellSelected() == true ){
				receiptsPanel.deleteSelectedCell();
				outputManager.updatePersonFile(person);
			}
			
		}
	}
	
	private void updatePersonData() {
		person.setFirstName(textAreaName.getText());
		person.setLastName(textAreaSurname.getText());
		person.setIncome( Double.valueOf(textIncome.getText()) );
		person.setIdentifyingNumber( Integer.valueOf(textId.getText()) );
	}
	

	public void hideCard() {
		this.setVisible(false);
	}
}
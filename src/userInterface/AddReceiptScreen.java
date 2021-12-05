package userInterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dataExport.OutputManager;
import dataManagement.Company;
import dataManagement.Person;
import dataManagement.Receipt;

public class AddReceiptScreen extends GridBagBasedScreen implements ActionListener {

	private JButton okButton;
	private JButton cancelButton;
	private JLabel explainAction;
	private JTextField idInput;
	private JTextField amountInput;
	private JTextField companyInput;
	private JComboBox<String> categoryInput;
	private String[] categories = { "Basic", "Entertainment", "Travel", "Health", "Other" };
	private ReceiptListModel receiptsListModel;
	
	OutputManager outputManager;
	private Person person;
	
	AddReceiptScreen(ReceiptListModel receiptListModel, Person person) {
		
		outputManager = new OutputManager();
		
		this.receiptsListModel = receiptListModel;
		this.person = person;
		
		this.setTitle("Add a new Receipt");
		this.setBounds(200, 200, 500, 300);
		this.setMinimumSize(new Dimension(500,300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		this.add(panel);
		
		explainAction = new JLabel("Enter the Data for a New Receipt:");
		
		// Presents the user with an "optional" randomly generated receipt id-number:
		JLabel idLabel = new JLabel("Id: ");
		idInput = new JTextField(String.valueOf((int)(Math.random() * ( 1000000 - 0 ))));
		
		JLabel amountLabel = new JLabel("Amount: ");
		amountInput = new JTextField();
		
		JLabel companyLabel = new JLabel("Company: ");
		companyInput = new JTextField();
		
		JLabel categoryLabel = new JLabel("Category: ");
		categoryInput = new JComboBox<String>(categories);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		addGridItem(panel, explainAction, 1, 1, 2, 1, GridBagConstraints.CENTER);
		
		addGridItem(panel, idLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, idInput, 1, 2, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, amountLabel, 0, 3, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, amountInput, 1, 3, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, companyLabel, 0, 4, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, companyInput, 1, 4, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, categoryLabel, 0, 5, 1, 1, GridBagConstraints.WEST);
		addGridItem(panel, categoryInput, 1, 5, 1, 1, GridBagConstraints.EAST);
		
		addGridItem(panel, okButton, 0, 6, 1, 1, GridBagConstraints.CENTER);
		addGridItem(panel, cancelButton, 1, 6, 1, 1, GridBagConstraints.CENTER);
		
		SwingUtilities.getRootPane(okButton).setDefaultButton(okButton);
		
		this.pack();
		this.setVisible(true);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == okButton ){
			
			if ( isNumeric(idInput.getText()) && isNumeric(amountInput.getText()) ) {
				Date date = new Date();
				Company company = new Company(companyInput.getText(), "no address");
				receiptsListModel.addElement(new Receipt(Integer.valueOf(idInput.getText()), date, (String)categoryInput.getSelectedItem(), Double.valueOf(amountInput.getText()), company));
				
				outputManager.updatePersonFile(person);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Error: Id and Amount should be number values.");
			}
			
		} else if ( e.getSource() == cancelButton ) {
			
			this.dispose();
		}
	}
}

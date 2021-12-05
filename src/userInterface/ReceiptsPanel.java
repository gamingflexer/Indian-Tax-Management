package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import dataManagement.Receipt;

public class ReceiptsPanel extends JPanel implements ActionListener {
	
	private ReceiptListModel model;
	private JList<String> list;

	ReceiptsPanel(ArrayList<Receipt> receiptsList) {
						
		list = new JList<String>();
		model = new ReceiptListModel(receiptsList);
		list.setModel(model);

		this.add(list);
	}
	
	public ReceiptListModel getModel () {
		return model;
	}
	
	public JList<String> getJList () {
		return list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Do nothing (yet).
	}

	public Boolean isAnyListCellSelected() {
		
		return !(list.isSelectionEmpty());
	}

	public void deleteSelectedCell() {
		
		model.removeItem(list.getSelectedIndex());
	}
}
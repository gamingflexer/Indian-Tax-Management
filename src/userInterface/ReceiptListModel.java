package userInterface;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import dataManagement.Receipt;

public class ReceiptListModel extends AbstractListModel<String> {
	
	private ArrayList<Receipt> myArrayList = new ArrayList<Receipt>();
	
	ReceiptListModel(ArrayList<Receipt> receiptList) {
		myArrayList = receiptList; 
	}

	@Override
	public int getSize() {
		return myArrayList.size();
	}

	@Override
	public String getElementAt(int index) {
		
		Receipt currentReceipt = myArrayList.get(index);
		
		String basicData = currentReceipt.getReceiptId().toString() +
				": "+ currentReceipt.getAmount().toString() + "$ at " + currentReceipt.getCompany().getName();

		return basicData;
	}
	
    public void addElement(Receipt obj) {
        myArrayList.add(obj);
        fireIntervalAdded(this, myArrayList.size()-1, myArrayList.size()-1);
    }
    
    public void addAll(ArrayList<Receipt> receiptList) {
        
    	for (Receipt receipt : receiptList) {
    		myArrayList.add(receipt);
    	}
        fireContentsChanged(this, 0, getSize());
    }

	public void removeItem(int indexOfElement) {
		
		Receipt selectedReceipt = myArrayList.get(indexOfElement);
	    
		Boolean removed = myArrayList.remove(selectedReceipt);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		
		fireIntervalRemoved( selectedReceipt, 0, getSize() );
	}
}
package dataManagement;

import java.io.File;
import java.util.ArrayList;

public class Person {

	// Constants for children-types initialization:
	public static final int MARRIED_FILING_JOINTLY = 1;
	public static final int MARRIED_FILING_SEPERATELY = 2;
	public static final int HEAD_OF_HOUSEHOLD = 3;
	public static final int slab_1 = 4;

	private String firstName;
	private String lastName;
	private Integer identifyingNumber;
	private Double income;
	private String personType;
	private ArrayList<Receipt> receipts = new ArrayList<Receipt>();
	private File file = null;

	public Person(String firstName, String lastName, String personType, Integer identifyingNumber, Object income) {

		this.firstName = firstName;
		this.lastName = lastName;
		setIdentifyingNumber(identifyingNumber);
		this.personType = personType;
		setIncome(income);
	}

	public Person() {
		this.firstName = "";
		this.lastName = "";
		this.identifyingNumber = getRandomId();
		this.income = 0d;
		this.personType = "slab_1";
	}

	private Integer getRandomId() {
		Double idNumberDouble = (Math.random() * (999999999 - 100000000));
		return idNumberDouble.intValue();
	}

	public Double calculateTaxBeforeReceipts(){

		if (personType.equals("slab_1")) return (slab_1());
		if (personType.equals("slab_2")) return (calculateTaxHeadOfHousehold());
		if (personType.equals("slab_3")) return (calculateTaxMarriedFilingJointly());
		return (calculateTaxMarriedFilingSeperately());
	}

	private Double slab_1() {

		if ( income >= 0 && income < 250000) return ( income * 0.0 );
		return (income);
	}
	
	private Double calculateTaxHeadOfHousehold() {
		
		if (income >= 0 && income > 250000) return (income * 0.15);
		return (income);
	}
	
	private Double calculateTaxMarriedFilingJointly() {
		
		if (income >= 0 && income > 250000) return (income * 0.20);
		return (income);
	}
	
	private Double calculateTaxMarriedFilingSeperately() {
		
		if (income >= 0 && income > 250000) return (income * 0.30);
		return (income);
	}

	public Double calculateFinalTax() {

		Double taxBeforeReceipts = calculateTaxBeforeReceipts();
		Double receiptAmount = calculateReceiptAmount();

		if (receiptAmount >= 0 && receiptAmount < (income * 0.2)) {
			return (taxBeforeReceipts + (taxBeforeReceipts * 0.08));
		}
		if (receiptAmount < (income * 0.4)) {
			return (taxBeforeReceipts + (taxBeforeReceipts * 0.04));
		}
		if (receiptAmount < (income * 0.6)) {
			return (taxBeforeReceipts - (taxBeforeReceipts * 0.15));
		}
		
		return (taxBeforeReceipts - (taxBeforeReceipts * 0.3));
	}

	// This one gives the total amount:
	public Double calculateReceiptAmount() {

		Double receiptsAmount = 0d;
		
		for (Receipt receipt : receipts) {
			receiptsAmount = receiptsAmount + receipt.getAmount();
		}

		return (receiptsAmount);
	}

	// Polymorphic alternative that gives the total of a specific category:
	public Double calculateReceiptAmount(String category) {

		Double receiptsAmount = 0d;

		for (Receipt receipt : receipts) {
			if (receipt.getCategory().equals(category)) {
				receiptsAmount = receiptsAmount + receipt.getAmount();
			}
		}

		return (receiptsAmount);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getIdentifyingNumber() {
		return identifyingNumber;
	}

	public void setIdentifyingNumber(Integer input) {
		if (input == 0) {
			this.identifyingNumber = getRandomId();
		} else {
			this.identifyingNumber = input;
		}
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Object input){
		if (input.getClass() != Double.class) {
			this.income = ((Integer) input).doubleValue();
		} else {
			this.income = (Double) input;
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void addReceipt(Receipt receipt) {
		receipts.add(receipt);
	}
	
	public void setCategory(String category) {
		this.personType = category;
	}
	
	public String getPersonCategory(){
		return this.personType;
	}

	public ArrayList<Receipt> getReceiptsList() {
		return receipts;
	}

	public void addReceiptsList(ArrayList<Receipt> importedReceiptsList) {
		receipts.addAll(importedReceiptsList);
	}
}

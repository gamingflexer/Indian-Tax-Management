package dataManagement;

import java.util.ArrayList;
import java.util.Date;

public class Receipt {

	private Integer receiptId;
	private Date dateOfIssue;
	private String category;
	private Double amount;
	private Company company;

	private ArrayList<String> categoryTypes = new ArrayList<String>();

	public Receipt(Integer receiptId, Date dateOfIssue, String category, Double amount, Company company) {

		initializeCategoryTypes();
		this.receiptId = receiptId;
		this.dateOfIssue = dateOfIssue;
		this.category = getCorrectCategory(category);
		this.amount = amount;
		this.company = company;
	}

	private void initializeCategoryTypes() {
		categoryTypes.add("Basic");
		categoryTypes.add("Entertainment");
		categoryTypes.add("Travel");
		categoryTypes.add("Health");
		categoryTypes.add("Other");
	}

	private String getCorrectCategory(String inputCategory) {

		for (String categoryName : categoryTypes) {
			if (categoryName.equals(inputCategory)) {
				return (categoryName);
			}
		}

		return ("Other");
	}

	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = getCorrectCategory(category);
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ArrayList<String> getCategories() {
		return categoryTypes;
	}
}

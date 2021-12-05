package dataExport;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


import dataManagement.Person;
import dataManagement.Receipt;

public class OutputManager {

	private Person person;
	private TagList tagListPersonData;
	private TagList tagListReceipts;

	private void doPersonFormatExporting(Person person) {
		extractPersonTags(person);
		convertReceiptsToTag(person.getReceiptsList());
	}

	private void extractPersonTags(Person person) {

		tagListPersonData = new TagList();
		tagListPersonData.add("Name", person.getFirstName()+" "+person.getLastName());
		tagListPersonData.add("AFM", person.getIdentifyingNumber().toString());
		tagListPersonData.add("Status", person.getPersonCategory());
		tagListPersonData.add("Income", person.getIncome().toString());
	}

	protected void convertReceiptsToTag(ArrayList<Receipt> receiptsList) {

		tagListReceipts = new TagList();

		if ((receiptsList != null) && (receiptsList.isEmpty() == false)) {
			for (Receipt receipt : receiptsList) {
				extractReceiptTags(receipt);
			}
		}
	}

	private void extractReceiptTags(Receipt receipt) {
		tagListReceipts.add("ReceiptID", receipt.getReceiptId().toString());
		DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
		tagListReceipts.add("Date", format.format(receipt.getDateOfIssue()));
		tagListReceipts.add("Kind", receipt.getCategory());
		tagListReceipts.add("Amount", receipt.getAmount().toString());
		tagListReceipts.add("Company", receipt.getCompany().getName());
		tagListReceipts.add("Country", " ");
		tagListReceipts.add("City", " ");
		tagListReceipts.add("Street", receipt.getCompany().getAddress());
		tagListReceipts.add("Number", " ");
	}

	private void doLogFormatExporting(Person person) {
		extractBasicPersonTags(person);
		extractReceiptCategoryTags(person);
	}

	private void extractBasicPersonTags(Person person){

		tagListPersonData = new TagList();

		tagListPersonData.add("Name", person.getFirstName()+" "+person.getLastName());
		tagListPersonData.add("AFM", person.getIdentifyingNumber().toString());
		tagListPersonData.add("Income", person.getIncome().toString());
		tagListPersonData.add("Basic Tax", person.calculateTaxBeforeReceipts().toString());
		Double tempTaxIncrease = person.calculateFinalTax() - person.calculateTaxBeforeReceipts();
		tagListPersonData.add("Tax Increase", tempTaxIncrease.toString());
		tagListPersonData.add("Total Tax", person.calculateFinalTax().toString());
	}

	private void extractReceiptCategoryTags(Person person) {

		tagListReceipts = new TagList();

		tagListReceipts.add("Total Receipts Gathered", person.calculateReceiptAmount().toString());
		tagListReceipts.add("Basic", person.calculateReceiptAmount("Basic").toString());
		tagListReceipts.add("Entertainment", person.calculateReceiptAmount("Entertainment").toString());
		tagListReceipts.add("Travel", person.calculateReceiptAmount("Travel").toString());
		tagListReceipts.add("Health", person.calculateReceiptAmount("Health").toString());
		tagListReceipts.add("Other", person.calculateReceiptAmount("Other").toString());
	}

	// This will check if the File to be saved needs TXT of XML format:
	public void savePersonToFile(Person personToSave, File file) {

		doPersonFormatExporting(personToSave);

		if (getFileExtension(file).equals("txt")) {

			OutputFileEncoder txtEncoder = new TxtEncoder(file.getAbsolutePath(), tagListPersonData, tagListReceipts);
			personToSave.setFile(file);

		} else if (getFileExtension(file).equals("xml")) {

			OutputFileEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), tagListPersonData, tagListReceipts);
			personToSave.setFile(file);
		}
	}

	public void savePersonToLogFile(Person personToSave, File file) {

		doLogFormatExporting(personToSave);

		if (getFileExtension(file).equals("txt")) {

			OutputFileEncoder txtEncoder = new TxtEncoder(file.getAbsolutePath(), tagListPersonData, tagListReceipts);

		} else if (getFileExtension(file).equals("xml")) {

			OutputFileEncoder xmlEncoder = new XmlEncoder(file.getAbsolutePath(), tagListPersonData, tagListReceipts);
		}
	}

	public void updatePersonFile(Person personToSave) {

		// An update will happen only if a file has already been saved:
		if (!(personToSave.getFile() == null)) {
			savePersonToFile(personToSave, personToSave.getFile());
		}
	}

	public String getPersonSuggestedFilename(Person personToSave, String type) {

		if (type.equals("xml")){
			return (personToSave.getIdentifyingNumber().toString() + "_INFO.xml");
		}
		if (type.equals("txt")){
			return (personToSave.getIdentifyingNumber().toString() + "_INFO.txt");
		}

		return (personToSave.getIdentifyingNumber().toString() + "_LOG.txt");

	}

	private String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		
		return ("");
	}
}

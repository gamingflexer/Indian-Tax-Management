package dataExport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import dataManagement.Person;

public abstract class OutputFileEncoder {

	protected File output;
	protected FileWriter fileWriter;
	protected Person person;
	protected StringBuilder stringBuilder = new StringBuilder();
	protected String totalOutput;
	private TagList tagListPersonData;
	private TagList tagListReceipts;

	abstract protected void filetypeSpecificEncodingProcess(String tagName);
	protected abstract void writeTag(String tagName, String includedData);

	public OutputFileEncoder(String fileOutputPath, TagList tagListPersonData, TagList tagListReceipts) {

		this.tagListPersonData = tagListPersonData;
		this.tagListReceipts = tagListReceipts;

		output = new File(fileOutputPath);
		String tagName = "Receipts";
		filetypeSpecificEncodingProcess(tagName);
		totalOutput = stringBuilder.toString();
		saveOutputToFile(output);
	}

	protected void writePersonTags() {
		writeTagList(tagListPersonData);
	}

	protected void writeReceiptTags() {
		writeTagList(tagListReceipts);
	}

	protected void writeTagList(TagList tagList) {

		Integer position = 0;

		while ( position < tagList.getSize() ){
			writeTag(tagList.get(1, position), tagList.get(2, position));
			position++;
		}
	}

	protected void saveOutputToFile(File outputFile) {
		try {
			fileWriter = new FileWriter(outputFile, false);
			fileWriter.append(totalOutput);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeFileWriter(fileWriter);
		}
	}

	private void closeFileWriter(FileWriter filewriter){
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
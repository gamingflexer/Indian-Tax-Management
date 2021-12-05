package dataExport;

public class TxtEncoder extends OutputFileEncoder {

	public TxtEncoder(String fileOutputPath, TagList tagListPersonData, TagList tagListReceipts) {
		super(fileOutputPath, tagListPersonData, tagListReceipts);
	}

	protected void filetypeSpecificEncodingProcess(String tagName){
		writePersonTags();
		writeOpeningTag(tagName);
		writeReceiptTags();
	}

	protected void writeTag(String tagName, String includedData) {

		if(tagName.equals("ReceiptID")){
			stringBuilder.append("Receipt ID" + ":");
		} else {
			stringBuilder.append(tagName + ":");
		}
		stringBuilder.append(" " + includedData + " ");
		stringBuilder.append(String.format("%n"));
	}

	private void writeOpeningTag(String tagName) {

		stringBuilder.append(tagName + ":");
		stringBuilder.append(String.format("%n"));
	}
}

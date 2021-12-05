package dataExport;

public class XmlEncoder extends OutputFileEncoder {

	public XmlEncoder(String fileOutputPath, TagList tagListPersonData, TagList tagListReceipts) {
		super(fileOutputPath, tagListPersonData, tagListReceipts);
	}

	protected void filetypeSpecificEncodingProcess(String tagName){
		writePersonTags();
		writeOpeningTag(tagName);
		writeReceiptTags();
		writeClosingTag(tagName);
	}

	protected void writeTag(String tagName, String includedData) {

		stringBuilder.append("<" + tagName + ">");
		stringBuilder.append(" " + includedData + " ");
		stringBuilder.append("</" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}

	private void writeOpeningTag(String tagName) {

		stringBuilder.append("<" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}

	private void writeClosingTag(String tagName) {

		stringBuilder.append("</" + tagName + ">");
		stringBuilder.append(String.format("%n"));
	}
}
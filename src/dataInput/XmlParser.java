// package dataInput;

// import java.io.File;

// public class XmlParser extends InputFileParser {

// 	public XmlParser(File filename) {
// 		super(filename);
// 	}

// 	protected void parseReceiptData() {

// 		consumeReceipt();

// 		while (isNextWordReceiptId()) {

// 			receiptId = checkForTagData("ReceiptID").trim();
// 			parseReceiptDataCommonCode();
// 		}
// 	}

// 	protected String checkForTagData(String tagElement) {

// 		getNextWord();
// 		String currentWord = "";

// 		if (word.equals("<" + tagElement + ">")) {

// 			getNextWord();

// 			while (!(word.equalsIgnoreCase("</" + tagElement + ">"))) {
// 				currentWord = currentWord + " " + word;
// 				getNextWord();
// 			}

// 			return currentWord;

// 		}

// 		return (null);
// 	}

// 	private Boolean isNextWordReceiptId() {

// 		getNextWord();

// 		if (word.equals("<ReceiptID>")) {
// 			parsedWordsIterator = parsedWordsIterator - 2;
// 			getNextWord();
// 			return (true);
// 		}

// 		return (false);
// 	}
// }

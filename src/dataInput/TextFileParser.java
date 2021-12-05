package dataInput;

import java.io.File;

public class TextFileParser extends InputFileParser {

	public TextFileParser(File filename) {
		super(filename);
	}

	protected void parseReceiptData() {

		consumeReceipt();

		while (isNextWordReceiptId()) {

			receiptId = checkForTagData("ID").trim();
			parseReceiptDataCommonCode();
		}
	}

	protected String checkForTagData(String tagElement) {

		getNextWord();
		String currentWord = "";

		if (word.equals(tagElement+":")){

			getNextWord();

			while(isNextWordValidAndNotReceipt()){
				currentWord = currentWord.concat(" " + word);
				getNextWord();
			}

			goToPreviousWord();
			return (currentWord);
		}
		
		return (null);
	}
	
	private Boolean isNextWordValidAndNotReceipt(){
		return (!(word.matches("\\w+:")) && !(word.equals("Receipt")) && !(isEndOfParsedWords()));
	}

	private Boolean isNextWordReceiptId() {

		getNextWord();

		if (word.equals("Receipt")){

			getNextWord();

			if(word.equals("ID:")){

				goToPreviousWord();
				return (true);
			}

		}

		return (false);
	}
}

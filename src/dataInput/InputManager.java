// package dataInput;

// import java.io.File;
// import dataManagement.PeopleManager;
// import dataManagement.Person;

// public class InputManager {

// 	private String filename;
// 	private InputFileParser inputFileParser;

// 	public Person importPersonFromFile(File file) {
		
// 		filename = file.getAbsolutePath();
// 		selectCorrectParser();

// 		try {
// 			Person newPerson = inputFileParser.getPerson();
// 			newPerson.setFile(file);
// 			return(newPerson);
			
// 		} catch (Exception e) {
// 			// In the event of invalid data input a placeholder Person is returned:
// 			return (PeopleManager.createNewPerson("slab_1", "No Name Found", "", 0, 0d));
// 		}
// 	}

// 	private void selectCorrectParser() {
		
// 		if (filename.substring(filename.lastIndexOf(".") + 1).equals("xml")){
// 			inputFileParser = new XmlParser(new File(filename));
// 		} else {
// 			inputFileParser = new TextFileParser(new File(filename));
// 		}
// 	}
// }

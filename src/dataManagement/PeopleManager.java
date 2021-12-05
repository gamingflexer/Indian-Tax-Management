package dataManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import dataManagement.Person;

public class PeopleManager {

	private ArrayList<Person> personList = new ArrayList<Person>();

	public static Person createNewPerson (int category, String firstName, String lastName, Integer identifyingNumber, Double income){

		ArrayList<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList("slab_3", "slab_4", "slab_2", "slab_1"));

		if ( (category-1) < categories.size()) {
			return(new Person(firstName, lastName, categories.get(category-1), identifyingNumber, income));
		}

		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return (new Person("", "", "slab_1", 0, 0));
	}

	public Person createNewPerson (int category) {
		return (createNewPerson(category, "", "", 0, 0.0));
	}

	public static Person createNewPerson (String categoryBeforeTrim, String firstName, String lastName, Integer identifyingNumber, Double income){

		String category = categoryBeforeTrim.trim();

		if (isValidCategory(category)){
			return (new Person(firstName, lastName, category, identifyingNumber, income));
		}

		System.out.println("Error: Problem with the Identification of Tax-Payer Category.");
		return (new Person(" ", " ", "slab_1", 0, 0));
	}

	private static Boolean isValidCategory(String category) {

		ArrayList<String> categories = new ArrayList<String>();
		categories.addAll(Arrays.asList("slab_1", "slab_2", "slab_3", "slab_4"));

		for (String current : categories) {
			if (current.equals(category)) {
				return (true);
			}
		}

		return (false);
	}

	public Company companyCreator(String name, String address) {
		
		if (Company.allCompaniesList != null) {
			ArrayList<Company> companyList = Company.allCompaniesList;

			for (int i=0; i<companyList.size(); i++) {
				if ((companyList.get(i).getName() == name) && (companyList.get(i).getAddress() == address)) {
					return companyList.get(i);
				}
			}
		}
		
		return (new Company(name, address));
	}
	
	public ArrayList<Person> getPersonList () {
		return personList;
	}
}

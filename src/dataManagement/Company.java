package dataManagement;

import java.util.ArrayList;

public class Company {

	protected static ArrayList<Company> allCompaniesList;

	private String name;
	private String address;

	public Company(String name, String address) {

		this.name = name;
		this.address = address;

		if (allCompaniesList != null) {
			allCompaniesList.add(this);
		}
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
}

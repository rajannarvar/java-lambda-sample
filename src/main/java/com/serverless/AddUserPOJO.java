package com.serverless;

public class AddUserPOJO {
	String name;
	String email;

	public AddUserPOJO(String name, String email) {
		// super();
		this.name = name;
		this.email = email;
	}

	public AddUserPOJO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
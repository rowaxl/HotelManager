package models;

public class Customer {
	
	private String emailAddress;
	private String fullName;
	private String phoneNum;
	
	public Customer(String emailAddress, String fullName, String phoneNum) {
		super();
		this.emailAddress = emailAddress;
		this.fullName = fullName;
		this.phoneNum = phoneNum;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}
	
	

}

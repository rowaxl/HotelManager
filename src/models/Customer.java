package models;

import helper.InvalidInputException;
import helper.Validator;

public class Customer {
	
	private String emailAddress;
	private String fullName;
	private String phoneNum;
	private final String DEFAULT_NAME = "Customer";
	
	public Customer(String emailAddress, String fullName, String phoneNum) {
		setFullName(fullName);
		try {
			setEmailAddress(emailAddress);
			setPhoneNum(phoneNum);
		} catch (InvalidInputException e) {
			System.err.println(e);
		}
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

	public void setEmailAddress(String emailAddress) throws InvalidInputException {
		if(Validator.validateEmailAdd(emailAddress)) {
			this.emailAddress = emailAddress;
		} else {
			throw new InvalidInputException("Invalid email address. Please try one more time.");
		}
	}

	public void setFullName(String fullName) {
		if(!fullName.contains("@")) {
			this.fullName = fullName;
		} else {
			this.fullName = DEFAULT_NAME;
		}
	}

	public void setPhoneNum(String phoneNum) throws InvalidInputException {
		if(Validator.validatePhoneNum(phoneNum)) {
			this.phoneNum = phoneNum;
		} else {
			throw new InvalidInputException("Invalid phone number. Please try one more time.");
		}
	}
	
}

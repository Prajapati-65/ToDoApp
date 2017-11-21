package com.bridgeit.springToDoApp.Utility.JMS;

import java.io.Serializable;

public class JmsData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private String emailId;
	
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


}

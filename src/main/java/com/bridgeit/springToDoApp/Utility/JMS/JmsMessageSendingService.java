package com.bridgeit.springToDoApp.Utility.JMS;

public interface JmsMessageSendingService {
	
	public void sendMessage(String emailId , String message);
	
}

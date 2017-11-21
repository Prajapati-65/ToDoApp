package com.bridgeit.springToDoApp.Utility.JMS;

public interface JmsMessageSendingService {
	
	public void sendMessage(String token,StringBuffer stringBuffer,String emailId);

}

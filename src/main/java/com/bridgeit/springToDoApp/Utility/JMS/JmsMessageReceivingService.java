package com.bridgeit.springToDoApp.Utility.JMS;


/**
 * @author Om Prajapati
 *
 */
public interface JmsMessageReceivingService {
	
	/**
	 * @param JmsData object
	 */
	public void messageReceive(JmsData jmsData);

}

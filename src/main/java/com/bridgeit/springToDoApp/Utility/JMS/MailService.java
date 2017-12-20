package com.bridgeit.springToDoApp.Utility.JMS;

/**
 * @author Om Prajapati
 *
 */
public interface MailService {

	/**
	 * @param String to
	 * @param String text
	 */
	public void sendEmail(String to, String text);
}

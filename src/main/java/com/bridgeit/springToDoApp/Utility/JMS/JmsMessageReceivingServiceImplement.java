package com.bridgeit.springToDoApp.Utility.JMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

/**
 * @author Om Prajapati
 *
 */
@Service
public class JmsMessageReceivingServiceImplement implements JmsMessageReceivingService {
	
	@Autowired
	MailService javaMail;

	@Override
	@JmsListener(destination="MessageStorage")
	public void messageReceive(JmsData jmsData) {
		
	
		String emailId=jmsData.getEmailId();
		
		String message = jmsData.getMessage();
		try {
			javaMail.sendEmail(emailId, message);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

}

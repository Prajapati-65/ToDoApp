package com.bridgeit.springToDoApp.Utility.JMS;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Om Prajapati
 *
 */
public class MailServiceImpl implements MailService {

	private MailSender email;

	public MailSender getEmail() {
		return email;
	}

	public void setEmail(MailSender email) {
		this.email = email;
	}
	
	@Async
	public void sendEmail(String to, String text) throws MailException {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Welcome to bridgelabz");
		message.setText(text);
		email.send(message);
	}

}

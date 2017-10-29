package com.bridgeit.springToDoApp.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailServiceImpl implements MailService {

	private MailSender email;

	public MailSender getEmail() {
		return email;
	}

	public void setEmail(MailSender email) {
		this.email = email;
	}
	
	String from = "om4java@gmail.com";
	String subject = "Welcome to Bridgelabz";
	String msg = "Registration successful";
	
	public void sendMail(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		email.send(message);
	}

}

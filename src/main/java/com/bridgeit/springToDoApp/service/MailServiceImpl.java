package com.bridgeit.springToDoApp.service;

import org.springframework.mail.MailException;
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

	public void sendEmail(String from, String to, String subject, String text) throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		email.send(message);
	}

}

package com.bridgeit.springToDoApp.service;

public interface MailService {

	public void sendEmail(String from, String to, String subject, String text);
}

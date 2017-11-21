package com.bridgeit.springToDoApp.Utility.JMS;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.bridgelabz.model.JmsData;

@Service
public class JmsMessageSendingServiceImplement implements JmsMessageSendingService {
	
	@Autowired
	private JmsTemplate jmsTemplate; 
	
	@Override
	public void sendMessage(String token, StringBuffer url,String emailId) {
		
		JmsData jmsData=new JmsData();
		jmsData.setEmailId(emailId);
		jmsData.setToken(token);
		jmsData.setUrl(url);
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message=session.createObjectMessage(jmsData);
				return message;
			}
		});
		
	}


}

package com.bridgeit.springToDoApp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.model.ErrorMessage;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.MailService;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.bridgeit.springToDoApp.validation.Validator;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	Validator validator;
	
	@Autowired
	ErrorMessage message;
	
	@Autowired
	MailService mailService;
	
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		String isValidator = validator.validateSaveUser(user);
		if (isValidator.equals("Success")) {
			userService.saveUser(user); 
			mailService.sendMail(user.getEmail());
			return new ResponseEntity<String>(isValidator,HttpStatus.OK);
		}
		return new ResponseEntity<String>(isValidator,HttpStatus.CONFLICT);
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorMessage> loginUser(@RequestBody User user, HttpSession session) {
		
		user = userService.loginUser(user);
		String generatetoken = GenerateJWT.generate(user.getId());
		
		session.setAttribute("user", user);
		message.setMessage(generatetoken);
		return new ResponseEntity<ErrorMessage>(message ,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> logout(HttpSession session) {
		session.removeAttribute("user");
		session.invalidate();
		message.setMessage("Logout seccessful");
		return new ResponseEntity<ErrorMessage>(message ,HttpStatus.OK);
	}

	
}

package com.bridgeit.springToDoApp.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;

public class Validator {

	@Autowired
	UserService userServive;
	
	public static final Pattern EMAIL_ID_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",Pattern.CASE_INSENSITIVE);
	public static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-Z]{2,}$");
	public static final Pattern MOBILE_REGEX = Pattern.compile("[0-9]{10}");
	public static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9]{8,}$");

	public String validateSaveUser(User user) {

		if (!validateRegEx(user.getFirstName(), NAME_REGEX)) {
			return "Your first name is too short...";
		} 
		else if (!validateRegEx(user.getLastName(), NAME_REGEX)) {
			return "your last name is too short...";
		} 
		else if (!validateRegEx(user.getEmail(), EMAIL_ID_REGEX)) {
			return "Please enter a valid email address !!";
		} 
		else if (!validateRegEx(String.valueOf(user.getMobileNumber()), MOBILE_REGEX)) {
			return "Contact number must be 10 digits !!";
		} 
		else if (!validateRegEx(user.getPassword(), PASSWORD_REGEX)) {
			return "Your password is short !!";
		} 
		else {
			
			User newUser= userServive.emailValidate(user.getEmail());
			if (newUser != null) {
				return "Email already exists";
			} else {
				return "Success";
			}
		}
	}

	boolean validateRegEx(String email, Pattern match) {
		Matcher matcher = match.matcher(email);
		return matcher.find();
	}

}

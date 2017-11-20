package com.bridgeit.springToDoApp.Utility;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;


public class Validator {

	@Autowired
	UserService userService;
	
	public String validateSaveUser(User user) {
		
		String result="false";
		
		String firstName="^[a-zA-Z]{2,}$";
		
		String lastName="^[a-zA-Z]{2,}$";
		
		String emailFormat="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		String passwordFormat="^[a-zA-Z0-9]{8,}$";
		
		String contactFormat="[0-9]{10}";
		
		if(user.getFirstName()==null||user.getFirstName()==""){
			result="Your first name is too short...";
			return result;
		}
		
		else if(user.getLastName()==null||user.getLastName()==""){
			result="Your last name is too short...";
			return result;
		}
		
		else if(user.getEmail()==null||user.getEmail()==""){
			result="Email is not in correct format.";
			return result;
		}
		
		else if(user.getPassword()==null||user.getPassword()==""){
			result="Password must contain words and number";
			return result;
		}
		
		else if(String.valueOf(user.getMobileNumber())==null||String.valueOf(user.getMobileNumber())==""){
			result="Contact cannot be empty.";
			return result;
		}
		
		else if(!user.getFirstName().matches(firstName)){
			result="Your first name is too short...";
			return result;
		}
		
		else if(!user.getLastName().matches(lastName)){
			result="Your last name is too short...";
			return result;
		}
		
		else if(!user.getEmail().matches(emailFormat)){
			result="Please enter a valid email address !!";
			return result;
		}
		
		else if(!user.getPassword().matches(passwordFormat)){
			result="Your password is short !!";
			return result;
		}
		
		else if(!String.valueOf(user.getMobileNumber()).matches(contactFormat)){
			result="Contact number must be 10 digits !!";
			return result;
		}
		
		else {
			
			User newUser= userService.emailValidate(user.getEmail());
			if (newUser != null) {
				result = "Email already exists";
				return result;
			} else {
				result = "Registration successful";
				return result;
			}
		}
	}
	
	public String validatePassword(String password) {
		String passwordFormat="^[a-zA-Z0-9]{8,}$";
		
		 if(!password.matches(passwordFormat)){
			 return "Your password is short !!";
			}
		 else{
			 return "true";
		 }
	}
}

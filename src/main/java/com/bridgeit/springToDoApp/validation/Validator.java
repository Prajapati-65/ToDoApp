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
			return "Your first name is short...";
		} 
		else if (!validateRegEx(user.getLastName(), NAME_REGEX)) {
			return "your last name is short...";
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
			User newUser = userServive.emailValidate(user.getEmail());
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
	
	/*@Autowired
	UserService userService;
	
	@Override
	public String validateSaveUser(User user) {
		String result="false";
		
		String nameFormat="^[a-zA-Z]+$";
		
		String contactValidation="[a-zA-Z]";
		
		String emailFormat="[a-zA-Z0-9\\.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,5}$";
		
		String passwordFormat="[a-zA-Z]+[0-9]+";
		
		String contactFormat="[0-9]{10}";
		
		if(user.getFirstName()==null||user.getFirstName()==""){
			result="First Name cannot be empty.";
			return result;
		}
		
		else if(user.getLastName()==null||user.getLastName()==""){
			result="Last Name cannot be empty.";
			return result;
		}
		
		else if(user.getEmail()==null||user.getEmail()==""){
			result="Email cannot be empty.";
			return result;
		}
		
		else if(user.getPassword()==null||user.getPassword()==""){
			result="Password cannot be empty.";
			return result;
		}
		
		else if(user.getContact()==null||user.getContact()==""){
			result="Contact cannot be empty.";
			return result;
		}
		
		else if(!user.getFirstName().matches(nameFormat)){
			result="First Name must contain only characters.";
			return result;
		}
		
		else if(!user.getLastName().matches(nameFormat)){
			result="Last Name must contain only characters.";
			return result;
		}
		
		else if(!user.getEmail().matches(emailFormat)){
			result="Email is not in correct format.";
			return result;
		}
		
		else if(!user.getPassword().matches(passwordFormat)){
			result="Password must contain words followed numbers.";
			return result;
		}
		
		else if(user.getContact().matches(contactValidation)){
			result="contact must contain numbers only.";
			return result;
		}
		
		else if(!user.getContact().matches(contactFormat)){
			result="contact must contain exectly 10 digits";
			return result;
		}

		else{
			User newUser=userService.emailValidation(user.getEmail());
			if(newUser==null){
				result="true";
				return result;
			}
			else{
				result="email already exists";
				return result;
			}		
		}
	}

	@Override
	public String validatePassword(String password) {
		
		String passwordFormat="[a-zA-Z]+[0-9]+";
		
		 if(!password.matches(passwordFormat)){
			 return "Password must contain words followed numbers.";
			}
		 else{
			 return "true";
		 }
	}
*/
	
	
	

}

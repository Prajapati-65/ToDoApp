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

import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/adduser/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> findById(@PathVariable("id") int id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/adduser/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email) {
		User user = userService.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public ResponseEntity<Void> saveUser(@RequestBody User user) {
		userService.saveUser(user);
		System.out.println("Registration seccessful");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> loginUser(@RequestBody User user) {
		userService.loginUser(user);
		System.out.println("Login seccessful");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Void> logout(HttpSession session) {
		session.removeAttribute("user");
		session.invalidate();
		System.out.println("Logout seccessful");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


}



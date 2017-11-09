package com.bridgeit.springToDoApp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgeit.springToDoApp.SocialUtility.FbLogin;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FacebookController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/facebookConnection", method = RequestMethod.GET)
	public void beforeFbLogin(HttpServletResponse response) throws IOException {
		String fbUrl = FbLogin.getFbLoginUrl();
		response.sendRedirect(fbUrl);
	}

	@RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
	public void afterFbLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String code;
		code = request.getParameter("code");
		System.out.println("Code : ---> "+code);
		
		String fbAccessToken = FbLogin.getFbAccessToken(code);
		
		System.out.println("FBAccess token : "+fbAccessToken);
		
		String profileData = FbLogin.getProfileData(fbAccessToken);
		System.out.println("profile is : "+profileData);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String email = objectMapper.readTree(profileData).get("email").asText();
		User user = userService.emailValidate(email);
		
		if (user == null) {
			
			user = new User();
			String firstName = objectMapper.readTree(profileData).get("first_name").asText();
			user.setFirstName(firstName);
			
			String lastName = objectMapper.readTree(profileData).get("last_name").asText();
			user.setLastName(lastName);
			
			user.setEmail(email);
			user.setActive(true);
			
			int userId = userService.saveUser(user);
			if (userId == 0) {
				response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
			} else {
				String accessToken = GenerateJWT.generate(userId);
				session.setAttribute("todoAppAccessToken", accessToken);
			}
		} else {
			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
		}
	}
	
	
	
	
}

package com.bridgeit.springToDoApp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.SocialUtility.GoogleLogin;
import com.bridgeit.springToDoApp.Utility.CustomResponse;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/googleConnection", method = RequestMethod.GET)
	public void beforeGoogle(HttpServletResponse response) throws IOException {
		String googleLoginPageUrl = GoogleLogin.generateLoginUrl();
		response.sendRedirect(googleLoginPageUrl);
	}

	@RequestMapping(value = "/googleLogin", method = RequestMethod.GET)
	public void afterLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		CustomResponse customResponse = new CustomResponse();
		
		if (request.getParameter("error") != null) {
			customResponse.setMessage(request.getParameter("error"));
		} else {

			String code;
			code = request.getParameter("code");
			System.out.println("Code is :-->" + code);

			String googleAccessToken = GoogleLogin.getAccessToken(code);
			System.out.println("Google Access Token is :--> " + googleAccessToken);

			String profileData = GoogleLogin.getProfileData(googleAccessToken);
			System.out.println("Profile data is : --> " + profileData);

			ObjectMapper objectMapper = new ObjectMapper();
			String email = objectMapper.readTree(profileData).get("email").asText();
			User user = userService.emailValidate(email);

			if (user == null) {
				user = new User();
				user.setEmail(email);
				String firstName = objectMapper.readTree(profileData).get("given_name").asText();
				user.setFirstName(firstName);
				String lastName = objectMapper.readTree(profileData).get("family_name").asText();
				user.setLastName(lastName);
				
				String profileImage = objectMapper.readTree(profileData).get("picture").asText();
				user.setProfileImage(profileImage);
				
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
}

package com.bridgeit.springToDoApp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.SocialUtility.GoogleLogin;
import com.bridgeit.springToDoApp.model.ErrorMessage;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/googlelogin", method = RequestMethod.GET)
	public void google(HttpServletResponse response) {
		String googleLoginPageUrl = GoogleLogin.generateLoginUrl();
		try {
			response.sendRedirect(googleLoginPageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/afterGoogleLogin", method = RequestMethod.GET)
	public void afterLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		ErrorMessage message = new ErrorMessage();
		if (request.getParameter("error") != null) {
			message.setMessage(request.getParameter("error"));
		} else {
			String code = request.getParameter("code");

			String googleAccessToken = GoogleLogin.getAccessToken(code);

			String profileData = GoogleLogin.getProfileData(googleAccessToken);

			ObjectMapper objectMapper = new ObjectMapper();

			try {
				String email = objectMapper.readTree(profileData).get("email").asText();
				User user = userService.emailValidate(email);

				if (user == null) {
					user = new User();

					user.setEmail(email);

					String firstName = objectMapper.readTree(profileData).get("given_name").asText();
					user.setFirstName(firstName);

					String lastName = objectMapper.readTree(profileData).get("family_name").asText();
					user.setLastName(lastName);

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

			} catch (IOException e) {
				e.printStackTrace();
				try {
					response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
}

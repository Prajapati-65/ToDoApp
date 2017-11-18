package com.bridgeit.springToDoApp.SocialLogin.FbSocialLogin.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgeit.springToDoApp.SocialLogin.FbSocialLogin.Model.FbLogin;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FacebookController {

	@Autowired
	UserService userService;

	private Logger logger = (Logger) LogManager.getLogger(FacebookController.class);
	
	@RequestMapping(value = "/facebookConnection", method = RequestMethod.GET)
	public void beforeFbLogin(HttpServletResponse response) throws IOException {
		String fbUrl = FbLogin.getFbLoginUrl();
		logger.info(fbUrl);
		response.sendRedirect(fbUrl);
	}

	@RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
	public void afterFbLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String code;
		code = request.getParameter("code");
		logger.info("Code : ---> "+code);
		
		String fbAccessToken = FbLogin.getFbAccessToken(code);
		logger.info("FBAccess token : "+fbAccessToken);
		
		String profileData = FbLogin.getProfileData(fbAccessToken);
		logger.info("profile is : "+profileData);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String email = objectMapper.readTree(profileData).get("email").asText();
		
		User user = userService.emailValidate(email);
		
		if (user == null) {
			
			user = new User();
			String firstName = objectMapper.readTree(profileData).get("first_name").asText();
			user.setFirstName(firstName);
			
			String lastName = objectMapper.readTree(profileData).get("last_name").asText();
			user.setLastName(lastName);
			
			String profileImage = objectMapper.readTree(profileData).get("picture").asText();
			user.setProfileImage(profileImage);
			
			user.setEmail(email);
			user.setActive(true);
			
			int userId = userService.saveUser(user);
			if (userId == 0) {
				response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
			} else {
				String accessToken = GenerateJWT.generate(userId);
				session.setAttribute("todoAppAccessToken", accessToken);
				response.sendRedirect("http://localhost:8080/ToDoApp/#!/home");
			}
		} else {
			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/home");
		}
	}
	
	
	
	
}
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
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.token.GenerateJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Om Prajapati
 *
 */
@Controller
public class FacebookController {

	@Autowired
	UserService userService;

	private Logger logger = (Logger) LogManager.getLogger(FacebookController.class);

	/**
	 * @param response
	 * @throws IOException
	 * 
	 * this method find the url data on the fb account
	 */
	@RequestMapping(value = "/facebookConnection", method = RequestMethod.GET)
	public void beforeFbLogin(HttpServletResponse response) throws IOException {
		String fbUrl = FbLogin.getFbLoginUrl();
		response.sendRedirect(fbUrl);
	}

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
	public void afterFbLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {

		String code;
		code = request.getParameter("code");

		String fbAccessToken = FbLogin.getFbAccessToken(code);

		String profileData = FbLogin.getProfileData(fbAccessToken);

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

			userService.saveUser(user, request);

			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/dummy");
		} else if(user!=null && user.getPassword()==null){
			String accessToken = GenerateJWT.generate(user.getId());
			session.setAttribute("todoAppAccessToken", accessToken);
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/dummy");
		} else{
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
		}
	}

}
package com.bridgeit.springToDoApp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.model.ErrorMessage;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.MailService;
import com.bridgeit.springToDoApp.service.UserService;
import com.bridgeit.springToDoApp.token.GenerateJWT;
import com.bridgeit.springToDoApp.token.VerifiedJWT;
import com.bridgeit.springToDoApp.validation.Validator;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Validator validator;

	@Autowired
	MailService mailService;

	@Autowired
	Encryption encryption;

	private Logger logger = (Logger) LogManager.getLogger(UserController.class);

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> saveUser(@RequestBody User user, HttpServletRequest request) {

		ErrorMessage errorMessage = new ErrorMessage();
		String isValidator = validator.validateSaveUser(user);
		if (isValidator.equals("Success")) {
			user.setActive(false);
			int id = userService.saveUser(user);
			logger.info("Registration successful");
			if (id != 0) {
				
				String activeToken = GenerateJWT.generate(id);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;
				
				try {
					mailService.sendEmail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz ",
							"Please click on this link within 1hours otherwise your account is not activated --> "
									+ url);
					logger.info("Please login email");
					errorMessage.setMessage("Please login email");
					return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.OK);
				} catch (MailException e) {
					logger.error("Mail don't send");
					e.printStackTrace();
				}
			}
		}
		errorMessage.setMessage("Email is already exit");
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<ErrorMessage> verifyMail(@PathVariable("activeToken") String activeToken,
			HttpServletResponse response) throws IOException {

		ErrorMessage errorMessage = new ErrorMessage();

		User user = null;
		int id = VerifiedJWT.verify(activeToken);
		try {
			user = userService.getUserById(id);
			logger.info("User details " + user);
		} catch (Exception e) {
			logger.error("user not found ");
			e.printStackTrace();
		}
		user.setActive(true);
		try {
			userService.updateUser(user);
			logger.info("Account is activated ");
		} catch (Exception e) {
			logger.error("Account is not activated");
			e.printStackTrace();
		}
		errorMessage.setStatus(200);
		logger.info("user Email id verified successfully now plzz login....");
		errorMessage.setMessage("user Email id verified successfully now plzz login....");
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorMessage> loginUser(@RequestBody User user, HttpSession session) {
		ErrorMessage errorMessage = new ErrorMessage();
		user = userService.loginUser(user);
		logger.info("Login successful " + user);
		String generatetoken = GenerateJWT.generate(user.getId());
		session.setAttribute("user", user);
		errorMessage.setMessage(generatetoken);
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> logout(HttpSession session) {
		ErrorMessage errorMessage = new ErrorMessage();
		session.removeAttribute("user");
		session.invalidate();
		errorMessage.setMessage("Logout seccessful");
		logger.info("Logout seccessful ");
		return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ErrorMessage forgotPassword(@RequestBody User user, HttpServletRequest request, HttpSession session) {

		ErrorMessage errorMessage = new ErrorMessage();
		String url = request.getRequestURL().toString();
		int lastIndex = url.lastIndexOf("/");
		String urlofForgotPassword = url.substring(0, lastIndex) + "#!/resetpassword";
		user = userService.emailValidate(user.getEmail());
		if (user == null) {
			errorMessage.setMessage("Please enter valid emailID");
			errorMessage.setStatus(500);
			logger.debug("Please enter valid emailID");
			return errorMessage;
		}
		try {
			String generateOTP = GenerateJWT.generate(user.getId());
			mailService.sendEmail("om4java@gmail.com", user.getEmail(), "",
					urlofForgotPassword + "  //Token= " + generateOTP);
		} catch (Exception e) {
			logger.error("email don't match");
			e.printStackTrace();
			errorMessage.setStatus(400);
			return errorMessage;
		}
		logger.info("Forgot password seccessful");
		errorMessage.setMessage("Forget Success");
		errorMessage.setStatus(200);
		return errorMessage;
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public ErrorMessage resetPassword(@RequestBody User user, HttpSession session) {

		ErrorMessage errorMessage = new ErrorMessage();

		String email = user.getEmail();
		String password = encryption.encryptPassword(user.getPassword());

		user = userService.emailValidate(email);
		if (user == null) {
			logger.error("User email is null " + user);
			errorMessage.setMessage("User not found :");
			errorMessage.setStatus(500);
			return errorMessage;
		}
		user.setPassword(password);
		if (userService.updateUser(user)) {
			logger.info("Password update is successful ");
			errorMessage.setMessage("Reset password is success :");
			errorMessage.setStatus(200);
			return errorMessage;
		} else {
			logger.error("Reset password unseccessful");
			errorMessage.setMessage("Password could not be changed");
			errorMessage.setStatus(-200);
			return errorMessage;
		}
	}

}

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

import com.bridgeit.springToDoApp.Utility.CustomResponse;
import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.Utility.ErrorResponse;
import com.bridgeit.springToDoApp.Utility.Response;
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
	public ResponseEntity<Response> saveUser(@RequestBody User user, HttpServletRequest request) {

		CustomResponse customResponse = new CustomResponse();
		
		//check the validation for user
		String isValidator = validator.validateSaveUser(user);
		if (isValidator.equals("Success")) {
			user.setActive(false);
			int id = userService.saveUser(user);
			logger.info("Registration successful");
			if (id != 0) {
				
				//create a template and send to the mail
				String activeToken = GenerateJWT.generate(id);
				String url = request.getRequestURL().toString();
				url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;

				try {
					mailService.sendEmail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz ",
							"Please click on this link within 1-hours otherwise your account is not activated--> "
									+ url);
					logger.info("Please login email");
					customResponse.setMessage("Registration successful..!");
					return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
				} catch (MailException e) {
					logger.error("Mail don't send");
					e.printStackTrace();
				}
			}
		}
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Email is already exit");
		return new ResponseEntity<Response>(errorResponse, HttpStatus.CONFLICT);

	}

	@RequestMapping(value = "/verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyMail(@PathVariable("activeToken") String activeToken,
			HttpServletResponse response) throws IOException {

		CustomResponse customResponse = new CustomResponse();
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

		customResponse.setStatus(200);
		logger.info("user Email id verified successfully now plzz login....");
		customResponse.setMessage("user Email id verified successfully now plzz login....");
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> loginUser(@RequestBody User user, HttpSession session) {
		CustomResponse customResponse = new CustomResponse();
		user = userService.loginUser(user);
		logger.info("Login successful " + user);
		String generatetoken = GenerateJWT.generate(user.getId());
		session.setAttribute("user", user);
		customResponse.setMessage(generatetoken);
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpSession session) {

		CustomResponse customResponse = new CustomResponse();
		session.removeAttribute("user");
		session.invalidate();
		customResponse.setMessage("Logout seccessful");
		logger.info("Logout seccessful ");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public Response forgotPassword(@RequestBody User user, HttpServletRequest request, HttpSession session) {

		CustomResponse customResponse = new CustomResponse();
		String url = request.getRequestURL().toString();
		int lastIndex = url.lastIndexOf("/");
		String urlofForgotPassword = url.substring(0, lastIndex) + "#!/resetpassword";
		
		user = userService.emailValidate(user.getEmail());
		if (user == null) {
			customResponse.setMessage("Please enter valid emailID");
			customResponse.setStatus(1);
			logger.debug("Please enter valid emailID");
			return customResponse;
		}
		try {
			String generateOTP = GenerateJWT.generate(user.getId());
			mailService.sendEmail("om4java@gmail.com", user.getEmail(), "",
					urlofForgotPassword + "  //Token= " + generateOTP);
		} catch (Exception e) {
			logger.error("email don't match");
			e.printStackTrace();
			customResponse.setStatus(0);
			return customResponse;
		}
		logger.info("Forgot password seccessful");
		customResponse.setMessage("Forget Success");
		customResponse.setStatus(2);
		return customResponse;
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public Response resetPassword(@RequestBody User user, HttpSession session) {

		CustomResponse customResponse = new CustomResponse();

		String email = user.getEmail();
		String password = encryption.encryptPassword(user.getPassword());
		
		user = userService.emailValidate(email);
		if (user == null) {
			logger.error("User email is null " + user);
			customResponse.setMessage("User not found :");
			customResponse.setStatus(1);
			return customResponse;
		}
		user.setPassword(password);
		if (userService.updateUser(user)) {
			logger.info("Password update is successful ");
			customResponse.setMessage("Reset password is success :");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			logger.error("Reset password unseccessful");
			customResponse.setMessage("Password could not be changed");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}

}

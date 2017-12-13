package com.bridgeit.springToDoApp.User.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.token.GenerateJWT;
import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;
import com.bridgeit.springToDoApp.Utility.Validator;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;

/**
 * @author Om Prajapati
 *
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Validator validator;

	@Autowired
	Encryption encryption;
	
	/**
	 * @param User object
	 * @param request
	 * @return User
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public Response saveUser(@RequestBody User user, HttpServletRequest request) {
		
		CustomResponse customResponse = new CustomResponse();
		String isValidator = validator.validateSaveUser(user);
		if (isValidator.equals("Registration successful"))
		{
			try {
				userService.saveUser(user, request);
				customResponse.setMessage("User registered successfully");
				customResponse.setStatus(2);
				return customResponse;
			} catch (Exception e) {
				customResponse.setMessage("User could not be registered");
				customResponse.setStatus(-1);
				return customResponse;
			}
		} else {

			customResponse.setMessage(isValidator);
			customResponse.setStatus(-6);
			return customResponse;
		}
	}
						
	/**
	 * @param String activeToken
	 * @param response
	 * @return user
	 * @throws IOException
	 */
	@RequestMapping(value = "verifyMail/{activeToken:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyMail(@PathVariable("activeToken") String activeToken,
			HttpServletResponse response) throws IOException 
	{
		CustomResponse customResponse = new CustomResponse();
		User user = null;
		int id = VerifiedJWT.verify(activeToken);
		try 
		{
			user = userService.getUserById(id);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		user.setActive(true);
		try 
		{
			userService.updateUser(user);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		customResponse.setStatus(200);
		
		customResponse.setMessage("user Email id verified successfully");
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
	}

	
	/**
	 * @param User object
	 * @param session
	 * @param request
	 * @return integer
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response loginUser(@RequestBody User user, HttpSession session, HttpServletRequest request) {
		
		Response response = new Response();
		try {
			int userId = userService.loginUser(user);
			if (userId == 0) {
				response.setMessage("Invalid details");
				response.setStatus(-5);
				return response;
			} else {
				String jwt = GenerateJWT.generate(userId);
				session.setAttribute("tokenlogin", jwt);
				response.setMessage(jwt);
				response.setStatus(2);
				return response;
			}
		} catch (Exception e) {
			response.setMessage("User from database is showing error");
			response.setStatus(-1);
			return response;
		}
	}
	
	/**
	 * @param session
	 * @return custom
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Response> logout(HttpSession session) 
	{
		
		CustomResponse customResponse = new CustomResponse();
		if(session!=null){
			
		session.removeAttribute("tokenlogin");
		session.removeAttribute("user");
		session.invalidate();
		customResponse.setMessage("Logout seccessful");
		
		return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		
		} else {
			customResponse.setMessage("Logout Unseccessful");
			return new ResponseEntity<Response>(customResponse, HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * @param user
	 * @param request
	 * @param session
	 * @return boolean
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public Response forgotPassword(@RequestBody User user, HttpServletRequest request, HttpSession session) 
	{
		CustomResponse customResponse = new CustomResponse();
		try {
			boolean successful = userService.forgotPassword(user , request);
			if (!successful) 
			{
				customResponse.setMessage("This email does not exist");
				customResponse.setStatus(-1);
			}
		} catch (MailException e) {
			
			customResponse.setMessage("Reset link could not be sent");
			customResponse.setStatus(-1);
			return customResponse;
		}
		customResponse.setMessage("Forgot password seccessful");
		customResponse.setStatus(1);
		return customResponse;
	}
	
	/**
	 * @param user
	 * @param request
	 * @return boolean
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
	public Response resetPassword(@RequestBody User user, HttpServletRequest request) {

		String	generateOTP=request.getHeader("token"); 
		
		int id=VerifiedJWT.verify(generateOTP);
		System.out.println("User id is :--> "+id);
		user=userService.getUserById(id);
		
		CustomResponse customResponse = new CustomResponse();
		
		boolean reset= userService.resetpassword(user);
		if(reset==true)
		{
			customResponse.setMessage("Reset password is success :");
			customResponse.setStatus(2);
			return customResponse;
		}
		else {
			customResponse.setMessage("Password could not be changed");
			customResponse.setStatus(-2);
			return customResponse;
		}
	}
	
	/**
	 * @param request
	 * @return User
	 * @throws IOException
	 */
	@RequestMapping(value = "/getUserDetails")
	public ResponseEntity<User> currrentUser(HttpServletRequest request) throws IOException {
		
		int userId = VerifiedJWT.verify(request.getHeader("token"));
		User user = userService.getUserById(userId);
		return ResponseEntity.ok(user);
	}
	

	/**
	 * @param session
	 * @return user
	 */
	@RequestMapping(value = "/social")
	public ResponseEntity<Response> social(HttpSession session) {
		System.out.println("session id2: " + session.getId());
		String token = (String) session.getAttribute("todoAppAccessToken");
		Response response = new Response();
		response.setMessage(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param user object
	 * @param request
	 * @return user
	 * @throws IOException
	 */
	@RequestMapping(value = "/profileChange", method = RequestMethod.PUT)
	public ResponseEntity<String> changeProfile(@RequestBody User user ,HttpServletRequest request) throws IOException {
		
		int userId = VerifiedJWT.verify(request.getHeader("token"));
		if(userId==0){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}
		userService.updateUser(user);
		return ResponseEntity.ok("");
	}
	
	/**
	 * @param request
	 * @return List of users
	 */
	@RequestMapping(value = "/listOfUserEmail" , method =RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUserEmail(HttpServletRequest request) {
		 
		int userId = VerifiedJWT.verify(request.getHeader("token"));
		if(userId == 0){
			return new ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST);
		}
		else {
			List<User> list = userService.getUserEmailId();
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		}
	}
	
	
}

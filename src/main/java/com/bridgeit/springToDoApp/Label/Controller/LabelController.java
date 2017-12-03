package com.bridgeit.springToDoApp.Label.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Label.Service.LabelService;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;
import com.bridgeit.springToDoApp.Utility.token.GenerateJWT;
import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
public class LabelController {
	
	
	@Autowired
	private LabelService labelService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getLabel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Label> getLabels(HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		String token = GenerateJWT.generate(userId);
		List<Label> list = null;
		try {
			int id = VerifiedJWT.verify(token);
			list = labelService.getAllLabels(id);
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
		}
		return list;	
	}
	
	@RequestMapping(value = "/getNotesOfLabel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Note> getAllNotesOfTheLabel(@RequestBody Label label, HttpServletRequest request){
		int userId = (int) request.getAttribute("userId");
		String token = GenerateJWT.generate(userId);
		List<Note> list = null;
		try {
			int id = VerifiedJWT.verify(token);
			list = labelService.getAllNotesOfThisLabel(label , id);
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/updateLabel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response updateLabel(@RequestBody Label label , HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		String token = GenerateJWT.generate(userId);
		Response response = new Response();
		try {
			int id = VerifiedJWT.verify(token);
			User user = userService.getUserById(id);
			
			user = userService.emailValidate(user.getEmail());
			label.setUser(user);
			labelService.updateLabel(label);
			response.setMessage("succesfull");
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			response.setMessage("Token Expired");
		}
		return response;
	}
	
	@RequestMapping(value = "/deleteLabel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response deleteLabel(@RequestBody Label label , HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		String token = GenerateJWT.generate(userId);
		Response response = new Response();
		try {
			int id = VerifiedJWT.verify(token);
			User user = userService.getUserById(id);
			user = userService.emailValidate(user.getEmail());
			label.setUser(user);
			labelService.deleteLabel(label);
			response.setMessage("succesfull");
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			response.setMessage("Token Expired");
		}
		return response;
	}
	
	@RequestMapping(value = "/createLabel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response createLabel(@RequestBody Label label, HttpServletRequest request) {
		String token = request.getHeader("token");
		Response response = new Response();
		try {
			int id = VerifiedJWT.verify(token);
			User user = userService.getUserById(id);
			user = userService.emailValidate(user.getEmail());

			label.setUser(user);
			labelService.createLabel(label);
			response.setMessage("succesfull");
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			response.setMessage("Token Expired");
		}
		return response;
	}
}

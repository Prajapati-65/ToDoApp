package com.bridgeit.springToDoApp.Label.Controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Label.Service.LabelService;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;

@RestController
@RequestMapping(value = "/user")
public class LabelController {

	@Autowired
	LabelService labelService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/saveLabel", method = RequestMethod.POST)
	public Response saveLabel(@RequestBody Label label, HttpServletRequest request) {

		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		label.setUserLabel(user);
		System.out.println("SET USER : "+label.getUserLabel());
		
		labelService.saveLabel(label);
		customResponse.setMessage("label save successfully:-");
		customResponse.setStatus(2);
		return customResponse;

	}

}
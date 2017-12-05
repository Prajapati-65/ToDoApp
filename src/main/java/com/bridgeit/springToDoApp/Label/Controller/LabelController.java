package com.bridgeit.springToDoApp.Label.Controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Label.Service.LabelService;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;

public class LabelController {

	@Autowired
	LabelService labelService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/addLabel", method = RequestMethod.POST)
	public Response addLabel(@RequestBody Label label, HttpServletRequest request) {
		
		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);

		if (user != null) {

			Set<Label> allLabels = labelService.getAllLabels(user.getId());
			
			if (allLabels != null) {
				Iterator<Label> itr = allLabels.iterator();
				while (itr.hasNext()) {

					Label oldLabel = (Label) itr.next();
					if (oldLabel.getLabelName().equals(label.getLabelName())) {
						customResponse.setMessage("Label is already exist");
						customResponse.setStatus(-5);
						return customResponse;
					}
				}
			}
			label.setUserLabel(user);
			int labelAddId = labelService.addLabel(label);
			if (labelAddId > 0) {
				customResponse.setMessage("Label is added");
				customResponse.setStatus(2);
				return customResponse;
			} else {
				customResponse.setMessage("Label is not added");
				customResponse.setStatus(-1);
				return customResponse;
			}
		} else {
			customResponse.setMessage("User is null");
			customResponse.setStatus(-5);
			return customResponse;
		}
	}

	@RequestMapping(value = "/deleteLabel", method = RequestMethod.DELETE)
	public Response deleteLabel(@RequestBody Label label, HttpServletRequest request) {
		
		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		if (user != null) {
			labelService.deleteLable(label);
			customResponse.setMessage("Label is deteted");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			customResponse.setMessage("Label is not deleted");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}

	@RequestMapping(value = "/updateLabel", method = RequestMethod.PUT)
	public Response updateLabel(@RequestBody Label label, HttpServletRequest request) {
		
		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		if (user != null) {
			labelService.updateLable(label);
			customResponse.setMessage("Label is updated");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			customResponse.setMessage("Label is not updated");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}
}
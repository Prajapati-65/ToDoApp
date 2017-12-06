package com.bridgeit.springToDoApp.Label.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
		try {
			if (!(label.getLabelName() == "" || label.getLabelName() == null)) {
				Label objLabel = labelService.getLabelByName(label.getLabelName());
				if (objLabel == null) {

					label.setUserLabel(user);
					labelService.saveLabel(label);
					customResponse.setMessage("Label add successful");
					customResponse.setStatus(2);
					return customResponse;
				} else {
					customResponse.setMessage("Label is already exist ");
					customResponse.setStatus(-1);
					return customResponse;
				}
			}
			customResponse.setMessage("Label can't be empty");
			customResponse.setStatus(-5);
			return customResponse;

		} catch (Exception e) {
			return customResponse;
		}
	}

	@RequestMapping(value = "getLabelNotes/{label}", method = RequestMethod.GET)
	public List<Label> getLabels(@PathVariable String label, HttpServletRequest request) {
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		List<Label> allNotes = labelService.getLabels(user);
		return allNotes;
	}

	@RequestMapping(value = "/deleteLabels/{id}", method = RequestMethod.DELETE)
	public Response deleteLabel(@PathVariable int id) {

		CustomResponse customResponse = new CustomResponse();
		boolean isDeleted = labelService.deleteLabelById(id);
		if (isDeleted) {
			customResponse.setMessage("Label deleted successful");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			customResponse.setMessage("Unable to delete");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}

	@RequestMapping(value = "/editLabel", method = RequestMethod.POST)
	public Response editNotes(@RequestBody Label label, HttpServletRequest request) {

		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		label.setUserLabel(user);
		
		boolean isEdited = labelService.editLabel(label);
		if (isEdited) {
			customResponse.setMessage("Editing note are successfull");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			customResponse.setMessage("edition is not possible");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}

}
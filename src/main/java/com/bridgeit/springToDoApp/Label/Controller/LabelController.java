package com.bridgeit.springToDoApp.Label.Controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/saveLabel", method = RequestMethod.POST)
	public Response saveLabel(@RequestBody Label labels, HttpServletRequest request) {
		
		CustomResponse customResponse = new CustomResponse();
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		try {
			if (!(labels.getLabelName() == "" || labels.getLabelName() == null)) {
				Label objLabel = labelService.getLabelByName(labels.getLabelName());
				if (objLabel == null) {
					labels.setUser(user);
					labelService.saveLabel(labels);
					customResponse.setMessage("label save successfully:-");
					customResponse.setStatus(2);
					return customResponse;
				} else {
					customResponse.setMessage("your label is already exist:-");
					customResponse.setStatus(-1);
					return customResponse;
				}
			}
			customResponse.setMessage("label can't be empty");
			customResponse.setStatus(-1);
			return customResponse;

		} catch (Exception e) {
			return customResponse;
		}
	}

	@RequestMapping(value = "getLabelNotes/{label}", method = RequestMethod.GET)
	public List<Label> getLabels(@PathVariable String label, HttpServletRequest request) {
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		List<Label> alNotes = labelService.getLabels(user);
		System.out.println("list of note label "+alNotes);
		return alNotes;
	}

	@RequestMapping(value = "/deleteLabels/{id}", method = RequestMethod.DELETE)
	public Response deleteLabel(@PathVariable int id) {
		CustomResponse customResponse = new CustomResponse();
		boolean isDeleted = labelService.deleteLabelById(id);
		if (isDeleted) {
			customResponse.setMessage("deleted successfully");
			customResponse.setStatus(2);
			return customResponse;
		} else {
			customResponse.setMessage("unable to delete");
			customResponse.setStatus(-1);
			return customResponse;
		}

	}

	@RequestMapping(value = "/editLabel", method = RequestMethod.POST)
	public Response editNotes(@RequestBody Label label, HttpServletRequest request) {

		CustomResponse customResponse = new CustomResponse();
		
		int id = (int) request.getAttribute("userId");
		User user = userService.getUserById(id);
		
		Label objLabel = labelService.getLabelById(label.getLabelId());
		label.setUser(user);
		
		Date resetDate = new Date();
		boolean isEdited = labelService.editLabel(label);
		if (isEdited) {
			customResponse.setMessage("editing notes are successfull");
			return customResponse;
		} else {
			customResponse.setMessage("edition is not possible");
			return customResponse;
		}
	}
	
	@RequestMapping(value = "/removeLabel/{id}", method = RequestMethod.DELETE)
	public Response removeLabel(@PathVariable("id") int noteId, HttpServletRequest request) {
		System.out.println("inside remove label controller "+noteId);
		CustomResponse customResponse = new CustomResponse();
		User user = (User) request.getAttribute("user");
		boolean isEdited = labelService.removeNoteId(noteId);
		//label.setUser(user);
		//boolean isEdited;
		//Date resetDate = new Date();
		//isEdited = labelService.editLabel(label);
		if (isEdited) {
			customResponse.setMessage("remove notes are successfull");
			return customResponse;
		} else {
			customResponse.setMessage("remove is not possible");
			return customResponse;
		}
	}
}
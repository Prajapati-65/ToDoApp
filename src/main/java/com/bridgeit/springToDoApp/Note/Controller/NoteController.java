package com.bridgeit.springToDoApp.Note.Controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.Note.Service.NoteService;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.User.Service.UserService;
import com.bridgeit.springToDoApp.Utility.ValidateNote;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;

@RestController
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public Response createNote(@RequestBody Note note, HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		CustomResponse customResponse = new CustomResponse();

		boolean validation = ValidateNote.validate(note);

		if (!validation) {
			customResponse.setMessage("Note cannot be empty");
			customResponse.setStatus(-5);
			return customResponse;
		}

		try {
			noteService.createNote(note, userId);
			customResponse.setMessage("Note added successfully");
			customResponse.setStatus(1);

			return customResponse;

		} catch (Exception e) {

			customResponse.setMessage("Note could not be added");
			customResponse.setStatus(-1);

			return customResponse;
		}
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Response deleteNote(@PathVariable("id") int noteId , HttpServletRequest request) {
		
		int userId = (int) request.getAttribute("userId");
		
		CustomResponse customResponse = new CustomResponse();
		try {
			boolean isDeleted = noteService.deleteNote(noteId, userId);
			System.out.println("isDeleted : "+isDeleted);
			if (!isDeleted) {
				customResponse.setMessage("Error deleting note");
				customResponse.setStatus(-1);
			}
			customResponse.setMessage("Note deleted successfully");
			customResponse.setStatus(1);
			return customResponse;
		} catch (Exception e) {
			customResponse.setMessage("Error deleting note");
			customResponse.setStatus(-1);
			return customResponse;
		}
	}
	
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response update(@RequestBody Note note, HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		CustomResponse customResponse = new CustomResponse();

		try {
			boolean isUpdated = noteService.updateNote(note , userId);
			System.out.println("is update " + isUpdated);
			if (!isUpdated) {
				customResponse.setMessage("User is not authorized");
				customResponse.setStatus(-1);
				return customResponse;
			}
			customResponse.setMessage("Note updated successfully");
			customResponse.setStatus(2);
			return customResponse;
			
		} catch (Exception e) {
			e.printStackTrace();
			customResponse.setMessage("Note could not be updated");
			customResponse.setStatus(-1);
			return customResponse;
		}

	}

	@RequestMapping(value = "/changeColor", method = RequestMethod.POST)
	public ResponseEntity<Response> updateColor(@RequestBody Note note, HttpServletRequest request) {
		CustomResponse customResponse = new CustomResponse();
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		note.setUser(user);
		noteService.updateNote(note ,userId);
		customResponse.setMessage("note updated.");
		customResponse.setNotes(null);
		return ResponseEntity.ok(customResponse);
	}
	
	
	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public ResponseEntity<List> getAllNotes(HttpServletRequest request) {
		
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		
		CustomResponse customResponse = new CustomResponse();
		
		List<Note> allNotes = noteService.getAllNotes(user);

		customResponse.setMessage("note found.");
		customResponse.setNotes(allNotes);

		return ResponseEntity.ok(customResponse.getNotes());
	}
}

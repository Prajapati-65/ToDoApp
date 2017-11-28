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
	public ResponseEntity<Response> updateColor(@RequestBody Note note, @RequestAttribute("loginedUser") User user) {
		CustomResponse customResponse = new CustomResponse();
		note.setUser(user);
		//noteService.updateNote(note);
		customResponse.setMessage("note updated.");
		customResponse.setNotes(null);
		return ResponseEntity.ok(customResponse);
	}
	
	
	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public Response getAllNotes(HttpSession session ,HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");
		
		CustomResponse customResponse = new CustomResponse();

		try {
			
			List<Note> notes = noteService.getAllNotes(userId);
			
			customResponse.setMessage("Notes loaded");
			customResponse.setStatus(1);
			customResponse.setNotes(notes);
			return customResponse;
			
		} catch (Exception e) {
			
			customResponse.setMessage("Notes could not be loaded");
			customResponse.setStatus(-1);
			return customResponse;
		}
		
	}
	
	

}

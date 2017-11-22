package com.bridgeit.springToDoApp.Note.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<Response> createNote(@RequestBody Note note, HttpServletRequest request) {
		
		int userId = (int) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		note.setUser(user);
		if (user != null) {
			Date date = new Date();
			note.setCreatedDate(date);
			note.setModifiedDate(date);
			noteService.createNote(note);
			CustomResponse  customResponse = new CustomResponse();
			customResponse.setMessage("Note create successfully");
			return new ResponseEntity<Response>(customResponse, HttpStatus.OK);
		}
        CustomResponse customResponse = new CustomResponse();
        customResponse.setMessage("Please login first");  
		return new ResponseEntity<Response>(customResponse, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId) {
		Note note = new Note();
		note.setNoteId(noteId);
		
		boolean delete = noteService.deleteNote(note);
		
		CustomResponse customResponse = new CustomResponse();
		
		if (delete != true) {
	
			customResponse.setMessage("Note could not be deleted");  
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
			
		} else {
			
			customResponse.setMessage("Note deleted successfully");
			return ResponseEntity.ok(customResponse);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Response> update(@RequestBody Note note) {

		int noteid =note.getNoteId();
		System.out.println("noteid: " + noteid);
		Note noteById = noteService.getNoteById(noteid);

		Date createDate = noteById.getCreatedDate();
		note.setCreatedDate(createDate);
		
		User user = noteById.getUser();
		note.setUser(user);
		
		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);
		
		boolean isUpdated = noteService.updateNote(note);
		
		CustomResponse customResponse = new CustomResponse();
		
		if (isUpdated != true) {

			customResponse.setMessage("Note could not be updated..."); 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customResponse);
			
		} else {
			
			customResponse.setMessage("Note updated successfully...");
			return ResponseEntity.ok(customResponse);
		}
	}

	/**
	 * @param note(note who's color has to be changes)
	 * @param user(user who is login)
	 * @return OK Status
	 */
	
	@RequestMapping(value = "/changeColor", method = RequestMethod.POST)
	public ResponseEntity<Response> updateColor(@RequestBody Note note, @RequestAttribute("loginedUser") User user) {
		CustomResponse customResponse = new CustomResponse();
		note.setUser(user);
		noteService.updateNote(note);
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

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.springToDoApp.Utility.CustomResponse;
import com.bridgeit.springToDoApp.Utility.ErrorResponse;
import com.bridgeit.springToDoApp.Utility.Response;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.Note.Service.*;
import com.bridgeit.springToDoApp.User.Model.User;

@RestController
@RequestMapping(value = "/user")
public class NoteController {
	
	@Autowired
	NoteService noteService;
	
	private Logger logger = (Logger) LogManager.getLogger(NoteController.class);
	
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public Response createNote(@RequestBody Note note , HttpServletRequest request)
	{
		int userId = (int) request.getAttribute("userId");
		
		CustomResponse customResponse = new CustomResponse();
		User user = new User();
		user.setId(userId);
		note.setUser(user);
		
		if(userId !=0)
		{
			noteService.createNote(note ,userId);
			logger.info("Note created successful");
			customResponse.setMessage("Note create successfully");
			return customResponse;
		}
		else{
		
			logger.info("Please login first");
			customResponse.setMessage("Please login first");  
			return customResponse;	
	
		}
	}
	
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Response deleteNote(@PathVariable("id") int noteId , HttpServletRequest request) {
		
		int userId = (int) request.getAttribute("userId");
		try {
			Response response = noteService.deleteNote(noteId, userId);
			return response;
			
		} catch (Exception e) {
			
			Response response = new Response();
			response.setMessage("Error deleting note");
			response.setStatus(1);
			return response;
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
		if (isUpdated != true) {
			logger.error("Note could not be updated...");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorMessage("Note could not be updated..."); 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			
		} else {
			logger.info("Note updated successfully...");
			CustomResponse customResponse = new CustomResponse();
			customResponse.setMessage("Note updated successfully...");
			return ResponseEntity.ok(customResponse);
		}
	}

	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public List<Note> getAllNotes(HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		List<Note> notes = noteService.getAllNotes(user);
		logger.info("All notes are : "+notes);
		return notes;
	}
	

	

}

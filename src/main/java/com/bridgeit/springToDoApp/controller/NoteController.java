package com.bridgeit.springToDoApp.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.bridgeit.springToDoApp.model.Note;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.NoteService;

@RestController
@RequestMapping(value = "/user")
public class NoteController {
	
	@Autowired
	NoteService noteService;

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody Note note, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
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
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Please login first");
		return new ResponseEntity<Response>(errorResponse, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int noteId) {
		Note note = new Note();
		note.setNoteId(noteId);
		boolean delete = noteService.deleteNote(note);
		if (delete != true) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Note could not be deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {
			CustomResponse customResponse = new CustomResponse();
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
		if (isUpdated != true) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Note could not be updated...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		} else {
			CustomResponse customResponse = new CustomResponse();
			customResponse.setMessage("Note updated successfully...");
			return ResponseEntity.ok(customResponse);
		}
	}

	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public List<Note> getAllNotes(HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		List<Note> notes = noteService.getAllNotes(user);
		return notes;
	}
	

	

}

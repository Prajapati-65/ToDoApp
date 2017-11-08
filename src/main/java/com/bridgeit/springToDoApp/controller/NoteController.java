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

import com.bridgeit.springToDoApp.model.ErrorMessage;
import com.bridgeit.springToDoApp.model.Note;
import com.bridgeit.springToDoApp.model.User;
import com.bridgeit.springToDoApp.service.NoteService;

@RestController
@RequestMapping(value = "/user")
public class NoteController {
	
	@Autowired
	NoteService noteService;

	@Autowired
	ErrorMessage message;

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> createNote(@RequestBody Note note, HttpSession session) {
		User user = (User) session.getAttribute("user");
		note.setUser(user);
		if (user != null) {
			Date date = new Date();
			note.setCreatedDate(date);
			note.setModifiedDate(date);
			noteService.createNote(note);
			message.setMessage("Note create successfully");
			return new ResponseEntity<ErrorMessage>(message, HttpStatus.OK);
		}
		message.setMessage("Please login first");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ErrorMessage> deleteNote(@PathVariable("id") int noteId) {
		Note note = new Note();
		note.setNoteId(noteId);
		boolean delete = noteService.deleteNote(note);
		if (delete != true) {
			message.setMessage("Note could not be deleted");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		} else {
			message.setMessage("Note deleted successfully");
			return ResponseEntity.ok(message);
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<ErrorMessage> update(@RequestBody Note note) {

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
			message.setMessage("Note could not be updated...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		} else {
			message.setMessage("Note updated successfully...");
			return ResponseEntity.ok(message);
		}
	}

	@RequestMapping(value = "/getallnotes", method = RequestMethod.GET)
	public List<Note> getAllNotes(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Note> notes = noteService.getAllNotes(user);
		return notes;
	}
	

	

}

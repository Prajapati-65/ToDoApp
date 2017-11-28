package com.bridgeit.springToDoApp.Note.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.DAO.NoteDao;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.Utility.ValidateNote;


public  class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	
	@Transactional
	public void createNote(Note note, int userId) {

		User user = new User();
		user.setId(userId);
		note.setUser(user);

		Date createdDate = new Date();
		note.setCreatedDate(createdDate);
		note.setModifiedDate(createdDate);;
		noteDao.createNote(note);
	}
	
	
	@Transactional
	public boolean updateNote(Note note, int userId) {

		Note oldNote = noteDao.getNoteById(note.getNoteId());

		boolean isNotEmpty = ValidateNote.validate(note);
		if (!isNotEmpty) {
			noteDao.deleteNote(oldNote);
			return true;
		}
		Date modifiedDate = new Date();
		note.setModifiedDate(modifiedDate);

		noteDao.updateNote(oldNote);
		return true;
		
	}
	
	@Transactional
	public boolean deleteNote(int noteId, int userId) {

		Note note = noteDao.getNoteById(noteId);

		if (note == null) {
			return false;
		}

		if (note.getUser().getId() != userId) {
			return false;
		}

		noteDao.deleteNote(note);

		return true;
	}
	
	
	
	@Transactional
	public List<Note> getAllNotes(int userId) {
		User user = new User();
		user.setId(userId);

		List<Note> notes = noteDao.getAllNotes(user);
		
		return notes;
	}

	
	@Transactional
	public Note getNoteById(int noteId) {

		return noteDao.getNoteById(noteId);
	}

	
}

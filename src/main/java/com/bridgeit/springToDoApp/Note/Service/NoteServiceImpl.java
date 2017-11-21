package com.bridgeit.springToDoApp.Note.Service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.DAO.NoteDao;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.Utility.Response;

public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	private Logger logger = (Logger) LogManager.getLogger(NoteServiceImpl.class);

	@Override
	@Transactional
	public Response createNote(Note note, int userId) {

		Response response = new Response();

		User user = new User();
		user.setId(userId);
		note.setUser(user);

		Date createdAt = new Date();
		note.setCreatedDate(createdAt);
		note.setModifiedDate(createdAt);
		noteDao.createNote(note);

		logger.info("Note added successfully");

		response.setMessage("Note added successfully");
		response.setStatus(1);

		return response;
	}

	
	
	
	
	@Override
	@Transactional
	public Response deleteNote(int noteId, int userId) {

		Response response = new Response();

		Note note = noteDao.getNoteById(noteId);

		if (note == null) {
			logger.info("Note does not exist");
			response.setMessage("Note does not exist");
			response.setStatus(-5);
			return response;
		}

		if (note.getUser().getId() != userId) {
			logger.warn("User is not authorised");
			response.setMessage("User is not authorised");
			response.setStatus(-4);
			return response;
		}
		
		noteDao.deleteNote(note);

		logger.info("Note deleted successfully");
		response.setMessage("Note deleted successfully");
		response.setStatus(1);
		return response;
	}	

	@Override
	@Transactional
	public Response getAllNotes(int userId) {
		User user = new User();
		user.setId(userId);

		List<Note> notes = noteDao.getAllNotes(user);

		Response response = new Response();
		logger.info("Notes loaded");
		response.setMessage("Notes loaded");
		response.setStatus(1);
		return response;
	}
	
	@Override
	@Transactional
	public boolean updateNote(Note note) {
		return noteDao.updateNote(note);
	}

	@Override
	@Transactional
	public Note getNoteById(int noteId){
		return noteDao.getNoteById(noteId);
	}
}

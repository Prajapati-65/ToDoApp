package com.bridgeit.springToDoApp.Note.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.DAO.NoteDao;
import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Log;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	public int createNote(Note note) {
		
		Log log = new Log();
		log.setAction("Create");
		log.setActionTime(note.getCreatedDate());
		int id = noteDao.createNote(note);
		note.setNoteId(id);
		log.setReferenceId(note);
		
		User user = note.getUser();
		log.setLogUser(user);
		noteDao.activity(log);
		return id;
	}

	public boolean updateNote(Note note) {

		Log log = new Log();
		log.setAction("Update");
		log.setActionTime(note.getModifiedDate());
		note.setNoteId(note.getNoteId());
		log.setReferenceId(note);
		
		User user =note.getUser();
		log.setLogUser(user);
		noteDao.activity(log);

		return noteDao.updateNote(note);
	}

	public boolean deleteNote(Note note) {

		return noteDao.deleteNote(note);

	}

	public Note getNoteById(int noteId) {

		return noteDao.getNoteById(noteId);
	}

	public List<Note> getAllNotes(User user) {

		return noteDao.getAllNotes(user);
	}

	public int saveCollborator(Collaborater collborate) {

		return noteDao.saveCollborator(collborate);
	}

	public List<User> getListOfUser(int noteId) {

		return noteDao.getListOfUser(noteId);
	}

	public List<Note> getCollboratedNotes(int userId) {

		return noteDao.getCollboratedNotes(userId);
	}

	public int removeCollborator(int shareWith, int noteId) {

		return noteDao.removeCollborator(shareWith, noteId);
	}

	public void deleteScheduleNote() {

		noteDao.deleteScheduleNote();
	}

	public void activity(Log log) {
		
		noteDao.activity(log);
	}

	public List<Log> getAllLog(User user) {
		
		return noteDao.getAllLog(user);
	}
}

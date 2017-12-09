package com.bridgeit.springToDoApp.Note.Service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.DAO.NoteDao;
import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.Utility.JsonResponse.CustomResponse;
import com.bridgeit.springToDoApp.Utility.JsonResponse.Response;


public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;

	public int createNote(Note note) {
		
		 return noteDao.createNote(note);
	}


	public boolean updateNote(Note note) {

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
	
	
	public int removeCollborator(int shareWith,int noteId){
		
		return noteDao.removeCollborator(shareWith, noteId);
	}
	
	public void deleteScheduleNote() { 
		
		noteDao.deleteScheduleNote();
	}
}

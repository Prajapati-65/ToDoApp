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

	@Transactional
	public int createNote(Note note) {
		
		 return noteDao.createNote(note);
	}

	@Transactional
	public boolean updateNote(Note note) {

		return noteDao.updateNote(note);
	}

	@Transactional
	public boolean deleteNote(Note note) {

		return noteDao.deleteNote(note);
	}

	@Transactional
	public Note getNoteById(int noteId) {

		return noteDao.getNoteById(noteId);
	}

	@Transactional
	public List<Note> getAllNotes(User user) {

		return noteDao.getAllNotes(user);
	}


	public int saveCollborator(Collaborater collborate) {
		
		return noteDao.saveCollborator(collborate);
	}


	public List<User> getListOfUser(int noteId) {
		
		return noteDao.getListOfUser(noteId);
	}

	
	public Set<Note> getCollboratedNotes(int userId) {
		
		return noteDao.getCollboratedNotes(userId);	
	}
	
	
	public int removeCollborator(int shareWith,int noteId){
		
		return noteDao.removeCollborator(shareWith, noteId);
	}
}

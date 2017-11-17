package com.bridgeit.springToDoApp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.dao.NoteDao;
import com.bridgeit.springToDoApp.model.Note;
import com.bridgeit.springToDoApp.model.User;


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

}

package com.bridgeit.springToDoApp.service;

import java.util.List;


import com.bridgeit.springToDoApp.model.Note;
import com.bridgeit.springToDoApp.model.User;


public interface NoteService {

	int createNote(Note note);
	
	boolean updateNote(Note note);

	boolean deleteNote(Note note);
	
	Note getNoteById(int noteId);

	List<Note> getAllNotes(User user);
	
	
}

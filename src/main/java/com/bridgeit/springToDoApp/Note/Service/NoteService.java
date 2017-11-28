package com.bridgeit.springToDoApp.Note.Service;

import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;

public interface NoteService {

	
	public void createNote(Note note, int userId);
	
	
	boolean updateNote(Note note, int userId);
	
	boolean deleteNote(int noteId , int userId);
	
	Note getNoteById(int noteId);

	//List<Note> getAllNotes(int userId);
	
	List<Note> getAllNotes(User user);
	
}

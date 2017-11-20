package com.bridgeit.springToDoApp.Note.DAO;


import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.Utility.Response;


public interface NoteDao {

int createNote(Note note);
	
	void updateNote(Note note);
	
	Note getNoteById(int noteId);
	
	void deleteNote(Note note);
	
	List<Note> getAllNotes(User user);

}

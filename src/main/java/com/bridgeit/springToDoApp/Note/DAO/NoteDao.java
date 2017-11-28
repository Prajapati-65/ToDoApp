package com.bridgeit.springToDoApp.Note.DAO;

import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


public interface NoteDao {

	int createNote(Note note);

	boolean updateNote(Note note);

	void deleteNote(Note note);

	Note getNoteById(int noteId);

	List<Note> getAllNotes(User user);

}

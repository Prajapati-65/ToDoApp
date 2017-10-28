package com.bridgeit.springToDoApp.dao;

import java.util.List;

import com.bridgeit.springToDoApp.model.Note;
import com.bridgeit.springToDoApp.model.User;

public interface NoteDao {

	int createNote(Note note);

	boolean updateNote(Note note);

	boolean deleteNote(Note note);

	Note getNoteById(int noteId);

	List<Note> getAllNotes(User user);

}

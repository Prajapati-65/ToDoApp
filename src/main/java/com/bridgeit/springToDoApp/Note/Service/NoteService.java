package com.bridgeit.springToDoApp.Note.Service;


import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.Utility.Response;

public interface NoteService {

	
	Response createNote(Note note, int userId);
	
	Response updateNote(Note note, int userId) throws Exception;
	
	/*Note getNoteById(int noteId);*/
	
	Response deleteNote(int noteId, int userId);
	
	Response getAllNotes(int userId);
	
	
}

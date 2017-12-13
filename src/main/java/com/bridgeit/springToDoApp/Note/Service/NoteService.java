package com.bridgeit.springToDoApp.Note.Service;

import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public interface NoteService {

	int createNote(Note note);
	
	boolean updateNote(Note note);

	boolean deleteNote(Note note);
	
	Note getNoteById(int noteId);

	List<Note> getAllNotes(User user);
	
	
	public int saveCollborator(Collaborater collborate);

	public List<User> getListOfUser(int noteId);
	

	public List<Note> getCollboratedNotes(int userId);
	
	public int removeCollborator(int shareWith,int noteId);
	
	
	public void deleteScheduleNote();
}

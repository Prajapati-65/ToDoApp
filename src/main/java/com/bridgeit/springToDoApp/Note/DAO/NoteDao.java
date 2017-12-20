package com.bridgeit.springToDoApp.Note.DAO;

import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Collaborater;
import com.bridgeit.springToDoApp.Note.Model.Log;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


public interface NoteDao {

	/**
	 * @param Note object
	 * @return integer
	 */
	int createNote(Note note);

	/**
	 * @param Note object
	 * @return boolean
	 */
	boolean updateNote(Note note);

	/**
	 * @param Note object
	 * @return boolean
	 */
	boolean deleteNote(Note note);

	
	/**
	 * @param integer noteId
	 * @return Note object
	 */
	Note getNoteById(int noteId);

	
	/**
	 * @param User object
	 * @return List of all Note
	 */
	List<Note> getAllNotes(User user);

	
	/**
	 * @param Collborator object
	 * @return integer
	 */
	public int saveCollborator(Collaborater collborate);
	
	/**
	 * @param integer  noteId
	 * @return List of User
	 */
	public List<User> getListOfUser(int noteId);
	
	/**
	 * @param integer userId
	 * @return List of Note
	 */
	public List<Note> getCollboratedNotes(int userId);
	
	/**
	 * @param integer shareWith
	 * @param integer noteId
	 * @return integer
	 */
	public int removeCollborator(int shareWith,int noteId);
	
	/**
	 * this method generate the scheduler time 
	 */
	public void deleteScheduleNote();
	
	
	public void activity(Log log);
	
	
	public List<Log> getAllLog(User user);
	
	
}

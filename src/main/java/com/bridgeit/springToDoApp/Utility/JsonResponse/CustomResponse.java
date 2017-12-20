package com.bridgeit.springToDoApp.Utility.JsonResponse;

import java.util.List;

import com.bridgeit.springToDoApp.Note.Model.Note;

/**
 * @author Om Prajapati
 *
 */
public class CustomResponse extends Response {

	List<Note>  notes;

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
 }

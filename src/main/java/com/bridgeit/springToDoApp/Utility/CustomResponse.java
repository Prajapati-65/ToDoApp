package com.bridgeit.springToDoApp.Utility;

import java.util.List;

import com.bridgeit.springToDoApp.model.Note;

public class CustomResponse extends Response {

	List<Note>  notes;

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
 }

package com.bridgeit.springToDoApp.Utility;

import com.bridgeit.springToDoApp.Note.Model.Note;

public class ValidateNote {

	public static boolean validate(Note note) {
		if (((note.getTitle() == null) || (note.getTitle().trim() == ""))) {
			if (((note.getDescription() == null) || (note.getDescription().trim() == ""))) {
				return false;
			}
		}
		return true;
	}
}

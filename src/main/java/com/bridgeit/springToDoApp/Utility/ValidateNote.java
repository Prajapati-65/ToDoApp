package com.bridgeit.springToDoApp.Utility;

import com.bridgeit.springToDoApp.Note.Model.Note;

/**
 * @author Om Prajapati
 *
 */
public class ValidateNote {

	/**
	 * @param Note object
	 * @return Boolean
	 */
	public static boolean validate(Note note) {
		if (((note.getTitle() == null) || (note.getTitle().trim() == ""))) {
			if (((note.getDescription() == null) || (note.getDescription().trim() == ""))) {
				return false;
			}
		}
		return true;
	}
}

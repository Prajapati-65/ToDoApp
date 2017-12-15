package com.bridgeit.springToDoApp.Note.Model;

public class Log {

	private int logId;

	private boolean createNote;

	private boolean updateNote;

	private boolean deleteNote;

	public int getLogId() {
		return logId;
	}

	public boolean isCreateNote() {
		return createNote;
	}

	public boolean isUpdateNote() {
		return updateNote;
	}

	public boolean isDeleteNote() {
		return deleteNote;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public void setCreateNote(boolean createNote) {
		this.createNote = createNote;
	}

	public void setUpdateNote(boolean updateNote) {
		this.updateNote = updateNote;
	}

	public void setDeleteNote(boolean deleteNote) {
		this.deleteNote = deleteNote;
	}

}

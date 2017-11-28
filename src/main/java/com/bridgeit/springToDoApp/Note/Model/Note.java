package com.bridgeit.springToDoApp.Note.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.bridgeit.springToDoApp.User.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "NOTE_TABLE")
@DynamicUpdate(true)
public class Note {

	@Id
	@GenericGenerator(strategy = "native", name = "noteGen")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "noteGen")
	@Column(name = "NOTE_ID")
	private int noteId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATE_DATE")
	private Date createdDate;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "PIN")
	private String pin;

	@Column(name = "ARCHIVE_STATUS")
	private String archiveStatus;

	@Column(name = "DELETE_STATUS")
	private String deleteStatus;

	@Column(name = "REMINDER_STATUS")
	private String reminderStatus;

	@Column(name = "NOTE_STATUS")
	private String noteStatus;

	@Column(name = "NOTE_COLOR")
	private String noteColor;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "USER_ID")
	private User user;


	public int getNoteId() {
		return noteId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getPin() {
		return pin;
	}

	public String getArchiveStatus() {
		return archiveStatus;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public String getReminderStatus() {
		return reminderStatus;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public String getNoteColor() {
		return noteColor;
	}

	public User getUser() {
		return user;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setPin(String pin) {
		if (pin.equals("true") || pin.equals("false")) {
			this.pin = pin;
		}
	}

	public void setArchiveStatus(String archiveStatus) {
		if (archiveStatus.equals("true") || archiveStatus.equals("false")) {
			this.archiveStatus = archiveStatus;
		}
	}

	public void setDeleteStatus(String deleteStatus) {
		if (deleteStatus.equals("true") || deleteStatus.equals("false")) {
			this.deleteStatus = deleteStatus;
		}
	}

	public void setReminderStatus(String reminderStatus) {
		if (reminderStatus.equals("true") || reminderStatus.equals("false")) {
			this.reminderStatus = reminderStatus;
		}
	}

	public void setNoteStatus(String noteStatus) {
		if (noteStatus.equals("true") || noteStatus.equals("false")) {
			this.noteStatus = noteStatus;
		}
	}

	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", pin=" + pin + ", archiveStatus=" + archiveStatus
				+ ", deleteStatus=" + deleteStatus + ", reminderStatus=" + reminderStatus + ", noteStatus=" + noteStatus
				+ ", noteColor=" + noteColor + ", user=" + user + "]";
	}

}

package com.bridgeit.springToDoApp.Note.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgeit.springToDoApp.User.Model.User;

@Entity
@Table
public class Collaborater {
	
	@Id
	@GenericGenerator(name = "col", strategy = "increment")
	@GeneratedValue(generator = "col")
	private int collaboratorId;
	
	@ManyToOne
	@JoinColumn
	private User ownerId;
	
	@ManyToOne
	@JoinColumn
	private User shareId;
	
	@ManyToOne
	@JoinColumn
	private Note noteId;

	public int getCollaboratorId() {
		return collaboratorId;
	}

	public User getOwnerId() {
		return ownerId;
	}

	public User getShareId() {
		return shareId;
	}

	public Note getNoteId() {
		return noteId;
	}

	public void setCollaboratorId(int collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public void setOwnerId(User ownerId) {
		this.ownerId = ownerId;
	}

	public void setShareId(User shareId) {
		this.shareId = shareId;
	}

	public void setNoteId(Note noteId) {
		this.noteId = noteId;
	}

	@Override
	public String toString() {
		return "Collaborater [collaboratorId=" + collaboratorId + ", ownerId=" + ownerId + ", shareId=" + shareId
				+ ", noteId=" + noteId + "]";
	}
	
}

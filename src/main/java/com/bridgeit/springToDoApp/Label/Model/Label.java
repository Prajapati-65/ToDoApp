package com.bridgeit.springToDoApp.Label.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LABEL")
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "mygen")
	@GenericGenerator(name = "mygen", strategy = "native")
	@Column(name = "LABEL_ID")
	private int labelId;

	@Column(name = "LABEL_NAME")
	private String labelName;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	@JsonIgnore
	private User userLabel;

	@JsonIgnore
	@ManyToMany(mappedBy = "labels")
	private Set<Note> noteId;
	
	
	
	public int getLabelId() {
		return labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public User getUserLabel() {
		return userLabel;
	}

	public Set<Note> getNoteId() {
		return noteId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setUserLabel(User userLabel) {
		this.userLabel = userLabel;
	}

	public void setNoteId(Set<Note> noteId) {
		this.noteId = noteId;
	}


}

package com.bridgeit.springToDoApp.Label.Model;

import java.util.HashSet;
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
@Table
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen")
	@GenericGenerator(name = "gen", strategy = "native")
	@Column(name = "LABEL_ID")
	private int labelId;

	@Column(name = "LABEL_NAME")
	private String labelName;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	@JsonIgnore
	private User user;

	@ManyToMany(mappedBy = "allLabels")
	@JsonIgnore
	private Set<Note> allNote = new HashSet<>();

	public int getLabelId() {
		return labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public User getUser() {
		return user;
	}

	public Set<Note> getAllNote() {
		return allNote;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAllNote(Set<Note> allNote) {
		this.allNote = allNote;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", user=" + user + ", allNote=" + allNote
				+ "]";
	}

}

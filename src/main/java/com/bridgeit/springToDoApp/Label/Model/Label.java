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
@Table(name = "LABEL_TABLE")
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
	private User user;

	@ManyToMany(mappedBy = "alLabels")
	@JsonIgnore
	private Set<Note> alNote = new HashSet<>();

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Note> getAlNote() {
		return alNote;
	}

	public void setAlNote(Set<Note> alNote) {
		this.alNote = alNote;
	}
	
	
	

}

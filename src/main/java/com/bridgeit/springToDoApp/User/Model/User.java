package com.bridgeit.springToDoApp.User.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Om Prajapati
 *
 */
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gen")
	@GenericGenerator(name = "gen", strategy = "native")
	@Column(name = "USER_ID")
	private int id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(unique = true, name = "USER_EMAIL")
	private String email;

	@Column(name = "MOBILE_NUMBER")
	private long mobileNumber;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	@Lob
	@Column(name = "PROFILE_IMAGE", columnDefinition = "LONGBLOB")
	private String profileImage;

	
	@OneToMany(mappedBy = "userLabel", fetch = FetchType.EAGER)
	private Set<Label> labels;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Note> note = new HashSet<Note>();
	
	/**
	 * generate getters and setters for all variables
	 *
	 */
	
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return isActive;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public Set<Label> getLabels() {
		return labels;
	}

	public Set<Note> getNote() {
		return note;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}

	public void setNote(Set<Note> note) {
		this.note = note;
	}

	

}

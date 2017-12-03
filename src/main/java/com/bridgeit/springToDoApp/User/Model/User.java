package com.bridgeit.springToDoApp.User.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "USER_TABLE")
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

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Label> labels;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Note> notes;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", password=" + password + ", isActive=" + isActive
				+ ", profileImage=" + profileImage + ", labels=" + labels + ", notes=" + notes + "]";
	}

}

package com.bridgeit.springToDoApp.Note.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgeit.springToDoApp.User.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LOG")
public class Log {

	@Id
	@GenericGenerator(strategy = "native", name = "log")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "log")
	@Column(name = "LOG_ID")
	private int logId;

	@Column(name = "ACTION_TIME")
	private Date actionTime;

	@Column(name = "ACTION")
	private String action;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "NOTE_ID")
	private Note referenceId;

	@ManyToOne
	@JsonIgnore
	private User logUser;

	@Column(name = "TITLE")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLogId() {
		return logId;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public String getAction() {
		return action;
	}

	public Note getReferenceId() {
		return referenceId;
	}

	public User getLogUser() {
		return logUser;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setReferenceId(Note referenceId) {
		this.referenceId = referenceId;
	}

	public void setLogUser(User logUser) {
		this.logUser = logUser;
	}

	@Override
	public String toString() {
		return "Log [logId=" + logId + ", actionTime=" + actionTime + ", action=" + action + ", referenceId="
				+ referenceId + ", logUser=" + logUser + ", title=" + title + "]";
	}

}

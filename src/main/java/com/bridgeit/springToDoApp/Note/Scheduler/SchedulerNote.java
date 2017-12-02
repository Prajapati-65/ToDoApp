package com.bridgeit.springToDoApp.Note.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Service.NoteService;

public class SchedulerNote {

	@Autowired
	NoteService noteService;
	
	public void noteSchedule() {
		
		noteService.deleteScheduleNote();
     
	}
}

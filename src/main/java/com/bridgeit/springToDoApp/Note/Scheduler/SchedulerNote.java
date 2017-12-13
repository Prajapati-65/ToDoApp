package com.bridgeit.springToDoApp.Note.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Service.NoteService;

/**
 * @author Om Prajapati
 *
 */
public class SchedulerNote {

	@Autowired
	NoteService noteService;
	
	public void noteSchedule() {
		
		noteService.deleteScheduleNote();
     
	}
}

package com.bridgeit.springToDoApp.Note.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Note.Service.NoteService;

public class SchedulerNote {

	@Autowired
	NoteService noteService;
	
	public void noteSchedule() {
		System.out.println("before spring scheduler");
		noteService.deleteScheduleNote();
        System.out.println("I am called by Spring scheduler");
	}
}

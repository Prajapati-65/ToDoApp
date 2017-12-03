package com.bridgeit.springToDoApp.Label.Service;

import java.util.List;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Note.Model.Note;


public interface LabelService {
	
public List<Label> getAllLabels(int userId);
	
	public List<Note> getAllNotesOfThisLabel(Label label , int userId);
	
	public void deleteLabel(Label label);
	
	public void createLabel(Label label);
	
	public void updateLabel(Label label);

}

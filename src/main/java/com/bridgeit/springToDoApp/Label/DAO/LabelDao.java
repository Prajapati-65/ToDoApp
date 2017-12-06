package com.bridgeit.springToDoApp.Label.DAO;

import java.util.List;
import java.util.Set;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;

public interface LabelDao {

	void saveLabel(Label labels);

	void deleteById(int id);

	List<Label> getLabels(User user);

	Label getLabelById(int labelId);


	Label getLabelByName(String labelName);

	public boolean removeNoteId(int id);

	boolean editLabel(Label label);
}

package com.bridgeit.springToDoApp.Label.Service;

import java.util.List;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public interface LabelService {
	
	void saveLabel(Label labels);

	boolean deleteLabelById(int id);

	List<Label> getLabels(User user);

	Label getLabelById(int labelId);

	Label getLabelByName(String labelName);

	boolean editLabel(Label label);

}

package com.bridgeit.springToDoApp.Label.DAO;

import java.util.List;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public interface LabelDao {

	void saveLabel(Label labels);

	void deleteById(int id);

	List<Label> getLabels(User user);

	Label getLabelById(int labelId);

	boolean editLabel(Label label);

	Label getLabelByName(String labelName);
}

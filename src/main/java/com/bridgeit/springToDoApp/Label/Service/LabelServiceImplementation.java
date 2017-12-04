package com.bridgeit.springToDoApp.Label.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.DAO.LabelDao;
import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public class LabelServiceImplementation implements LabelService {

	@Autowired
	LabelDao labelDoa;
	
	@Override
	public void saveLabel(Label labels) {
		labelDoa.saveLabel(labels);
	}

	@Override
	public boolean deleteLabelById(int id) {
		
		return labelDoa.deleteLabelById(id);
	}

	@Override
	public List<Label> getLabels(User user) {
		
		return labelDoa.getLabels(user);
	}

	@Override
	public Label getLabelById(int labelId) {
		
		return labelDoa.getLabelById(labelId);
	}

	@Override
	public Label getLabelByName(String labelName) {
		
		return labelDoa.getLabelByName(labelName);
	}

	@Override
	public boolean editLabel(Label label) {
		
		return labelDoa.editLabel(label);
	}

	

}

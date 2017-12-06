package com.bridgeit.springToDoApp.Label.Service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.DAO.LabelDao;
import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public class LabelServiceImplementation implements LabelService {

	@Autowired
	LabelDao labelDao;

	@Override
	public void saveLabel(Label labels) {
		labelDao.saveLabel(labels);
	}

	@Override
	public boolean deleteLabelById(int id) {
		labelDao.deleteById(id);
		return true;
	}

	@Override
	public List<Label> getLabels(User user) {
		return labelDao.getLabels(user);
	}

	@Override
	public Label getLabelById(int labelId) {
		return labelDao.getLabelById(labelId);
	}

	@Override
	public boolean editLabel(Label label) {
		labelDao.editLabel(label);
		return true;
	}

	@Override
	public Label getLabelByName(String labelName) {
		return labelDao.getLabelByName(labelName);
	}


}

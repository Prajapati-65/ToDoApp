package com.bridgeit.springToDoApp.Label.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.springToDoApp.Label.DAO.LabelDao;
import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Note.Model.Note;

@Service
@Transactional
public class LabelServiceImplementation implements LabelService {
	
	@Autowired
	private LabelDao labelDao;
	
	@Override
	public List<Label> getAllLabels(int userId) {
		return labelDao.getAllLabels(userId);
	}

	@Override
	public List<Note> getAllNotesOfThisLabel(Label label , int userId) {
		return labelDao.getAllNotesOfThisLabel(label , userId);
	}

	@Override
	public void deleteLabel(Label label) {
		labelDao.deleteLabel(label);
	}

	@Override
	public void createLabel(Label label) {
		labelDao.createLabel(label);
	}

	@Override
	public void updateLabel(Label label) {
		labelDao.updateLabel(label);
	}

}

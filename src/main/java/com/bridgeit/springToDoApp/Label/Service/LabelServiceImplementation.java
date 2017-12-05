package com.bridgeit.springToDoApp.Label.Service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.DAO.LabelDao;
import com.bridgeit.springToDoApp.Label.Model.Label;

public class LabelServiceImplementation implements LabelService {

	@Autowired
	LabelDao labelDoa;

	@Override
	@Transactional
	public int addLabel(Label label) {

		return labelDoa.addLabel(label);
	}

	@Override
	@Transactional
	public boolean deleteLable(Label label) {

		return labelDoa.deleteLable(label);
	}

	@Override
	@Transactional
	public boolean updateLable(Label label) {

		return labelDoa.updateLable(label);
	}

	@Override
	@Transactional
	public Set<Label> getAllLabels(int userId) {

		return labelDoa.getAllLabels(userId);
	}

}

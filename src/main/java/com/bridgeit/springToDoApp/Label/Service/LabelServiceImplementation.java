package com.bridgeit.springToDoApp.Label.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.springToDoApp.Label.DAO.LabelDao;
import com.bridgeit.springToDoApp.Label.Model.Label;

public class LabelServiceImplementation implements LabelService {

	@Autowired
	LabelDao labelDao;

	@Override
	public void saveLabel(Label labels) {
		labelDao.saveLabel(labels);
	}


}

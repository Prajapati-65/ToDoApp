package com.bridgeit.springToDoApp.Label.DAO;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.springToDoApp.Label.Model.Label;

public class LabelDaoImplement implements LabelDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void saveLabel(Label label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			System.out.println("inside save label");
			session.save(label);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}

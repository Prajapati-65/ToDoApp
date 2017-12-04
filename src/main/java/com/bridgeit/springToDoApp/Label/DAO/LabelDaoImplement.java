package com.bridgeit.springToDoApp.Label.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public class LabelDaoImplement implements LabelDao {

	@Autowired
	SessionFactory sessionFactory;
	
	Transaction transaction;
	
	@Override
	public void saveLabel(Label label) {
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			System.out.println("inside save label");
			session.save(label);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(int id) {
		Session session = sessionFactory.openSession();
		transaction = (Transaction) session.beginTransaction();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eq("id", id));
		Label labels = (Label) criteria.uniqueResult();

		try {
			session.delete(labels);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public List<Label> getLabels(User user) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eqOrIsNull("user", user));
		List<Label> labels = criteria.list();
		return labels;
	}

	@Override
	public Label getLabelById(final int labelId) {
		Label objLabel = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			objLabel = (Label) session.get(Label.class, labelId);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return null;
			}
			e.printStackTrace();
		}
		return objLabel;
	}

	@Override
	public Label getLabelByName(String labelName) {
		Label objLabel = null;
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eq("labelName", labelName));
		objLabel = (Label) criteria.uniqueResult();
		session.close();
		return objLabel;
	}
	

	@Override
	public boolean editLabel(Label label) {
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			session.update(label);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return false;
			}
			e.printStackTrace();
		}
		return true;
	}

}

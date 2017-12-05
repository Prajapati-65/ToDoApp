package com.bridgeit.springToDoApp.Label.DAO;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public class LabelDaoImplement implements LabelDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public int addLabel(Label label) {
		int labelId = 0;
		Session session = sessionFactory.getCurrentSession();
		labelId = (Integer) session.save(label);
		return labelId;
	}

	public boolean updateLable(Label label) {

		boolean status = false;
		Session session = sessionFactory.getCurrentSession();
		Label completlabel = session.get(Label.class, label.getLabelId());
		completlabel.setLabelName(label.getLabelName());
		session.update(completlabel);
		status = true;

		return status;
	}

	public boolean deleteLable(Label label) {

		boolean status = false;
		Session session = sessionFactory.getCurrentSession();
		try {
			Label completlabel = session.get(Label.class, label.getLabelId());
			session.delete(completlabel);
			status = true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return status;
	}

	public Set<Label> getAllLabels(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userId);
		Set<Label> label = user.getLabels();
		System.out.println(label);
		return label;
	}

}

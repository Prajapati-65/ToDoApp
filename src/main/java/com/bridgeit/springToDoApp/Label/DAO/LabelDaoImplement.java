package com.bridgeit.springToDoApp.Label.DAO;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.Note.Model.Note;
import com.bridgeit.springToDoApp.User.Model.User;


@Repository
public class LabelDaoImplement implements LabelDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Label> getAllLabels(int userId) {
		@SuppressWarnings("unchecked")
		List<User> list = sessionFactory.getCurrentSession().createQuery("from User where id=:User_Id")
				.setParameter("User_Id", userId).list();
		User user = list.get(0);
		user.getLabels().size();
		return user.getLabels();
	}

	@Override
	public List<Note> getAllNotesOfThisLabel(Label label, int userId) {
		@SuppressWarnings("unchecked")
		List<Label> list = sessionFactory.getCurrentSession()
				.createQuery("from Label where labelId=:Label_Id and id=:User_Id")
				.setParameter("Label_Id", label.getLabelId()).setParameter("User_Id", userId).list();
		label = list.get(0);
		return null;
	}

	@Override
	public void deleteLabel(Label label) {
		sessionFactory.getCurrentSession().delete(label);
	}

	@Override
	public void createLabel(Label label) {
		sessionFactory.getCurrentSession().save(label);
	}

	@Override
	public void updateLabel(Label label) {
		sessionFactory.getCurrentSession().update(label);
	}
}

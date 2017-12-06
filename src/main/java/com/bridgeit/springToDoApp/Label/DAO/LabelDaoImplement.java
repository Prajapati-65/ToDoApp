package com.bridgeit.springToDoApp.Label.DAO;

import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

public class LabelDaoImplement implements LabelDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void saveLabel(Label label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = (Transaction) session.beginTransaction();
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
		Transaction transaction = (Transaction) session.beginTransaction();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eq("id", id));
		Label labels = (Label) criteria.uniqueResult();
		
		try {
			System.out.println("inside delete label dao impl"+labels.getLabelId());
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
		// transaction = (Transaction) session.beginTransaction();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eqOrIsNull("user", user));
		List<Label> labels = criteria.list();
		return labels;
	}

	@Override
	public Label getLabelById(final int labelId) {
		Label objLabel = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = (Transaction) session.beginTransaction();
		try {
			objLabel = (Label) session.get(Label.class, labelId);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				session.close();
				return null;
			}
			e.printStackTrace();
		}
		return objLabel;
	}

	@Override
	public boolean editLabel(Label label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = (Transaction) session.beginTransaction();
		try {
			session.update(label);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				try {
					transaction.rollback();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				session.close();
				return false;
			}
			e.printStackTrace();
		}
		return true;

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
	public boolean removeNoteId(int id) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = (Transaction) session.beginTransaction();
		try {
			
			Query query = session
					.createQuery("delete id Label where id="+ id);

			
			int status = query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

}

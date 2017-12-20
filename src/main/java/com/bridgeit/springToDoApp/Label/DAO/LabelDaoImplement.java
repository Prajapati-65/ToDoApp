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

/**
 * @author OM Prajapati
 *
 */
public class LabelDaoImplement implements LabelDao {

	@Autowired
	SessionFactory sessionFactory;
	
	Transaction transaction;
	

	@Override
	public void saveLabel(Label label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			session.save(label);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void deleteById(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction =  session.beginTransaction();
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
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eq("userLabel", user));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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
	public boolean editLabel(Label label) {
		Session session = sessionFactory.openSession();
		if(label!=null){
			try {
				transaction = session.beginTransaction();
				session.update(label);			
				transaction.commit();
				return true;
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public Label getLabelByName(String labelName) {
		
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Label.class);
		criteria.add(Restrictions.eq("labelName", labelName));
		
		Label objLabel = (Label) criteria.uniqueResult();
		session.close();
		return objLabel;
	}
	

}

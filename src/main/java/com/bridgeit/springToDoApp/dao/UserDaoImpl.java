package com.bridgeit.springToDoApp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory factory;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public void saveUser(User user) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
	}

	public User loginUser(User user) {
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		criteria.add(Restrictions.eq("password", user.getPassword()));

		User finalUser = (User) criteria.uniqueResult();
		if (finalUser == null) {
			session.close();
			return null;
		}
		session.close();
		return finalUser;
	}

	public User logoutUser(User user) {
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		criteria.add(Restrictions.eq("password", user.getPassword()));
		User finalUser = (User) criteria.uniqueResult();
		if (finalUser == null) {
			session.close();
			return null;
		}
		session.close();
		return finalUser;
	}

	@Override
	public User emailValidation(String email) {
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		User user = (User) criteria.uniqueResult();
		session.close();
		return user;
	}

	@Override
	public User getUserById(int id) {
		Session session = factory.openSession();
		User user = session.get(User.class, id);
		System.out.println("User is: " + user);
		session.close();
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return false;
			}
		}
		session.close();
		return true;
	}

	
}

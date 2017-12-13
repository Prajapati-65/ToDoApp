package com.bridgeit.springToDoApp.User.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory factory;

	@Autowired
	Encryption encryption;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public int saveUser(User user) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int id = (int) session.save(user);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}

	
	public User loginUser(User user) {
		Session session = factory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", user.getEmail()));
		criteria.add(Restrictions.eq("password", encryption.encryptPassword(user.getPassword())));
		criteria.add(Restrictions.eq("isActive", true));
		User finalUser = (User) criteria.uniqueResult();
		if (finalUser == null) {
			session.close();
			return null;
		}
		session.close();
		return finalUser;
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
		}
		session.close();
		return true;
	}

	
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
	public List<User> getUserEmailId() {
		Session  session = factory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.setProjection(Projections.property("email"));
		List<User> userList = criteria.list();
		return userList;
	}
	
	

}

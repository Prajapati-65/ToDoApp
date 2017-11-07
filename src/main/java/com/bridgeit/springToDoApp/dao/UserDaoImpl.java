package com.bridgeit.springToDoApp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.model.Token;
import com.bridgeit.springToDoApp.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory factory;
	
	@Autowired
	Encryption encryption;
	
	
/*	

	@Autowired
	private RedisTemplate<String, Object> template;
	private HashOperations<String, String, Token> hashOperations;
	
*/

	
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
			if (user.getPassword() != null) {
				user.setPassword(encryption.encryptPassword(user.getPassword()));
			}
			int id =(int) session.save(user);
			transaction.commit();
			return id;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				return 0;
			}
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

	
	
	
	/*@Override
	public void addToken(Token token) {
		hashOperations = template.opsForHash();
		hashOperations.put(key, token.getAccessToken(), token);
	}

	@Override
	public Token getToken(String accessToken) {

		hashOperations = template.opsForHash();
		Token token = hashOperations.get(key, accessToken);
		return token;
	}
*/

	
}

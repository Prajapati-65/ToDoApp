package com.bridgeit.springToDoApp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.dao.UserDao;
import com.bridgeit.springToDoApp.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Transactional
	public int saveUser(User user) {

		return userDao.saveUser(user);
	}

	@Transactional
	public User loginUser(User user) {
		return userDao.loginUser(user);
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		
		return userDao.getUserById(id);
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {
		
		return userDao.updateUser(user);
	}
	
	@Transactional
	public User emailValidate(String email) {
		User user = userDao.emailValidation(email);
		if(user!=null){
			return user;
		}
		return null;
	}

}

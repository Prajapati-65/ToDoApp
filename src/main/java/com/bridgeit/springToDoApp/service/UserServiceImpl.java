package com.bridgeit.springToDoApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.dao.UserDao;
import com.bridgeit.springToDoApp.model.Token;
import com.bridgeit.springToDoApp.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public int saveUser(User user) {

		return userDao.saveUser(user);
	}

	public User loginUser(User user) {
		return userDao.loginUser(user);
	}

	@Override
	public User getUserById(int id) {
		
		return userDao.getUserById(id);
	}

	@Override
	public boolean updateUser(User user) {
		
		return userDao.updateUser(user);
	}
	
	public User emailValidate(String email) {
		User user = userDao.emailValidation(email);
		if(user!=null){
			return user;
		}
		return null;
	}

}

package com.bridgeit.springToDoApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.dao.UserDao;
import com.bridgeit.springToDoApp.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public User findById(int id) {
		return userDao.findById(id);
	}

	public User findByEmail(String email) {

		return userDao.findByEmail(email);
	}

	public void saveUser(User user) {

		userDao.saveUser(user);
	}

	public User loginUser(User user) {
		return userDao.loginUser(user);
	}

}

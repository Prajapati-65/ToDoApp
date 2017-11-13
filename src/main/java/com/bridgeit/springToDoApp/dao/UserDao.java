package com.bridgeit.springToDoApp.dao;

import com.bridgeit.springToDoApp.model.User;

public interface UserDao {

	int saveUser(User user);

	User loginUser(User user);

	User getUserById(int id);

	boolean updateUser(User user);
	
	public User emailValidation(String email);
}

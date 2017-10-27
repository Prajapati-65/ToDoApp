package com.bridgeit.springToDoApp.dao;

import com.bridgeit.springToDoApp.model.User;

public interface UserDao {

	void saveUser(User user);
		
	User loginUser(User user);

	User findById(int id);

	User findByEmail(String email);
	
}

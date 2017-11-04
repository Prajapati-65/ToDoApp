package com.bridgeit.springToDoApp.dao;

import com.bridgeit.springToDoApp.model.User;

public interface UserDao {

	void saveUser(User user);

	User loginUser(User user);

	User emailValidation(String email);

	User getUserById(int id);

	boolean updateUser(User user);

	

}

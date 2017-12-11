package com.bridgeit.springToDoApp.User.DAO;

import java.util.List;

import com.bridgeit.springToDoApp.User.Model.User;

public interface UserDao {

	int saveUser(User user);

	User loginUser(User user);

	User getUserById(int id);

	boolean updateUser(User user);
	
	public User emailValidation(String email);
	
	public List<User> getUserEmailId();
}

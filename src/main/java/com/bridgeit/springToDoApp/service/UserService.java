package com.bridgeit.springToDoApp.service;


import com.bridgeit.springToDoApp.model.User;


public interface UserService {

	int saveUser(User user);

	User loginUser(User user);

	User getUserById(int id);

	boolean updateUser(User user);

	public User emailValidate(String email);
}

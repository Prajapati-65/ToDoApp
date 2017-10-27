package com.bridgeit.springToDoApp.service;

import com.bridgeit.springToDoApp.model.User;

public interface UserService {
	
	void saveUser(User user);
	
	User findById(int id);

	User findByEmail(String email);
	
	User loginUser(User user);
}

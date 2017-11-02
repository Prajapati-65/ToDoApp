package com.bridgeit.springToDoApp.service;

import com.bridgeit.springToDoApp.model.User;

public interface UserService {

	void saveUser(User user);

	User loginUser(User user);

	User emailValidate(String email);
}

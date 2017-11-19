package com.bridgeit.springToDoApp.User.Service;


import com.bridgeit.springToDoApp.User.Model.User;


public interface UserService {

	int saveUser(User user);

	User loginUser(User user);

	User getUserById(int id);

	boolean updateUser(User user);

	public User emailValidate(String email);
}

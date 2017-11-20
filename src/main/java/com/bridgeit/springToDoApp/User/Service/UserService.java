package com.bridgeit.springToDoApp.User.Service;


import javax.servlet.http.HttpServletRequest;

import com.bridgeit.springToDoApp.User.Model.User;


public interface UserService {

	void saveUser(User user , HttpServletRequest request);

	int loginUser(User user);

	public boolean forgotPassword(User user , HttpServletRequest request);
		
	public boolean resetpassword(User user);
	
	User getUserById(int id);

	boolean updateUser(User user);

	public User emailValidate(String email);
}

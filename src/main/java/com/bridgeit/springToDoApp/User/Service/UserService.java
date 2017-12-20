package com.bridgeit.springToDoApp.User.Service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgeit.springToDoApp.User.Model.User;


/**
 * @author Om Prajapti
 *
 */
public interface UserService {

	/**
	 * @param User object
	 * @param request
	 */
	void saveUser(User user , HttpServletRequest request);

	/**
	 * @param User object
	 * @return integer
	 */
	int loginUser(User user);

	/**
	 * @param User object   
	 * @param request
	 * @return boolean
	 */
	public boolean forgotPassword(User user , HttpServletRequest request);
		
	/**
	 * @param User object
	 * @return boolean
	 */
	public boolean resetpassword(User user);
	
	/**
	 * @param integer id
	 * @return user
	 */
	User getUserById(int id);

	/**
	 * @param User object
	 * @return boolean
	 */
	boolean updateUser(User user);

	/**
	 * @param String email
	 * @return User object
	 */
	public User emailValidate(String email);
	
	/**
	 * @return List of all users
	 */
	public List<User> getUserEmailId();
}

package com.bridgeit.springToDoApp.User.DAO;

import java.util.List;

import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public interface UserDao {

	/**
	 * @param user object
	 * @return integer
	 */
	int saveUser(User user);

	/**
	 * @param User object
	 * @return User
	 */
	User loginUser(User user);

	/**
	 * @param integer id
	 * @return user
	 */
	User getUserById(int id);

	/**
	 * @param user object
	 * @return boolean
	 */
	boolean updateUser(User user);
	
	/**
	 * @param String email
	 * @return user
	 */
	public User emailValidation(String email);
	
	/**
	 * @return List of users
	 */
	public List<User> getUserEmailId();
}

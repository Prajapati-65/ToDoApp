package com.bridgeit.springToDoApp.User.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.springToDoApp.User.DAO.UserDao;
import com.bridgeit.springToDoApp.User.Model.User;
import com.bridgeit.springToDoApp.Utility.Encryption;
import com.bridgeit.springToDoApp.Utility.UrlTemplate;
import com.bridgeit.springToDoApp.Utility.token.GenerateJWT;
import com.bridgeit.springToDoApp.Utility.token.VerifiedJWT;
import com.bridgeit.springToDoApp.service.MailService;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	Encryption encryption;

	@Autowired
	MailService mailService;

	@Transactional
	public void saveUser(User user, HttpServletRequest request) {
		
		user.setActive(false);
		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		int userID = userDao.saveUser(user);
		if (userID != 0) {
			String activeToken = GenerateJWT.generate(userID);
			String url = UrlTemplate.urlTemplate(request);
			url = url + "verifyMail/" + activeToken;
			mailService.sendEmail("om4java@gmail.com", user.getEmail(), "Welcome to bridgelabz ",
					"Please click on this link within 1-hours otherwise your account is not activated--> " + url);
		}
	}

	@Transactional
	public int loginUser(User user) {
		
		User loggedInUser = userDao.emailValidation(user.getEmail());
		
		if (loggedInUser == null) {
			return 0;
		}
		
		if (!loggedInUser.isActive()) {
			return 0;
		}
		return loggedInUser.getId();
	}

	@Transactional
	public boolean forgotPassword(User user, HttpServletRequest request) {
		
		user = userDao.emailValidation(user.getEmail());
		
		if (user == null) {
			return false;
		}
		String generateOTP = GenerateJWT.generate(user.getId());
		String url = UrlTemplate.urlTemplate(request);
		mailService.sendEmail("om4java@gmail.com", user.getEmail(), "", url + " //Token--> " + generateOTP);
		return true;
	}

	@Override
	@Transactional
	public boolean resetpassword(User user ) {
		
		user = userDao.emailValidation(user.getEmail());
		if (user == null) {
			return false;
		}

		String pwd = encryption.encryptPassword(user.getPassword());
		user.setPassword(pwd);
		userDao.updateUser(user);
		return true;
	}

	@Override
	@Transactional
	public User getUserById(int id) {

		return userDao.getUserById(id);
	}

	@Override
	@Transactional
	public boolean updateUser(User user) {

		return userDao.updateUser(user);
	}

	@Transactional
	public User emailValidate(String email) {
		User user = userDao.emailValidation(email);
		if (user != null) {
			return user;
		}
		return null;
	}

}

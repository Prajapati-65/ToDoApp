package com.bridgeit.springToDoApp.Utility;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

/**
 * @author Om Prajapati
 *
 */
@Component
public class Encryption {
	
	/**
	 * @param String password
	 * @return String
	 */
	public String encryptPassword(String password) {
		
		String generatedPassword = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] passBytes = md.digest();
			StringBuilder sb = new StringBuilder();
			int len = passBytes.length;
			
			for (int i = 0; i < len; i++) {
				sb.append(Integer.toString((passBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			generatedPassword = sb.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return generatedPassword;
	}
}


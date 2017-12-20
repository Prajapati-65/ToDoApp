package com.bridgeit.springToDoApp.Utility.token;


import java.util.Date;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Om Prajapati
 *
 */
public class GenerateJWT {

	

	private static final String KEY = "application";
	
	/**
	 * @param integer id
	 * @return String
	 */
	public static String generate(int id){
		
		Date issueDate = new Date();
		
		Date expireDate = new Date(issueDate.getTime()+1000*60*60);
	
		JwtBuilder builder = Jwts.builder();
		builder.setSubject("accessToken");
	
		builder.setIssuedAt(issueDate);
		
		builder.setExpiration(expireDate);
		
		builder.setIssuer(String.valueOf(id));
		
		builder.signWith(SignatureAlgorithm.HS256, KEY);
		String compactJwt = builder.compact();
		return compactJwt;
	}
	
}
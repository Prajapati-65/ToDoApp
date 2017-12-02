package com.bridgeit.springToDoApp.Utility.token;


import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.bridgeit.springToDoApp.Utility.Filter.TokenInterceptor;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateJWT {

	
	private static Logger logger = (Logger) LogManager.getLogger(GenerateJWT.class);

	private static final String KEY = "application";
	
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
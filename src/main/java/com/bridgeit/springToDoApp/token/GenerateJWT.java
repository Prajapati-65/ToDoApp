package com.bridgeit.springToDoApp.token;


import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.bridgeit.springToDoApp.Filter.TokenInterceptor;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateJWT {

	
	private static Logger logger = (Logger) LogManager.getLogger(GenerateJWT.class);

	private static final String KEY = "application";
	
	public static String generate(int id){
		
		Date issueDate = new Date();
		logger.info("Issue date ->"+issueDate);
		
		Date expireDate = new Date(issueDate.getTime()+1000*60*60);
		logger.info("Expire date ->"+expireDate);
	
		JwtBuilder builder = Jwts.builder();
		builder.setSubject("accessToken");
	
		builder.setIssuedAt(issueDate);
		
		builder.setExpiration(expireDate);
		
		builder.setIssuer(String.valueOf(id));
		logger.info("Issue id -->"+id);
		
		builder.signWith(SignatureAlgorithm.HS256, KEY);
		String compactJwt = builder.compact();
		logger.info("Generated jwt : " + compactJwt);
		return compactJwt;
	}
	
}
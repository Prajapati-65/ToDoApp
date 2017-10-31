package com.bridgeit.springToDoApp.token;

import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateJWT {

private static final String KEY = "application";
	
	public static String generate(int id){
		
		Date issueDate = new Date();
		System.out.println("Issue date -->"+issueDate);
		
		Date expireDate = new Date(issueDate.getTime()+1000*60*30);
		System.out.println("Expire date -->"+expireDate);

		
		JwtBuilder builder = Jwts.builder();
		builder.setSubject("accessToken");
	
		builder.setIssuedAt(issueDate);
		
		builder.setExpiration(expireDate);
		
		builder.setIssuer(String.valueOf(id));
		System.out.println("Issue id -->"+id);
		
		builder.signWith(SignatureAlgorithm.HS256, KEY);
		String compactJwt = builder.compact();
		
		System.out.println("Generated jwt : " + compactJwt);
		return compactJwt;
	}
	
}

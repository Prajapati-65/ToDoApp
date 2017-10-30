package com.bridgeit.springToDoApp.token;


import java.security.Key;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class GenerateJWT {

private static final String KEY = "ToDoApp";
	
	public static String generate(int id){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		
		JwtBuilder builder = Jwts.builder();
		builder.setSubject("accessToken");
		builder.setIssuer(String.valueOf(id));
		builder.signWith(signatureAlgorithm, KEY);
		
		String compactJwt = builder.compact();
		System.out.println("Generated jwt: " + compactJwt);
		return compactJwt;
	}
	
}

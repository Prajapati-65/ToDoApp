package com.bridgeit.springToDoApp.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class VerifiedJWT {
	
	private static final String KEY = "ToDoApp";

	public static int verify(String token) {
		
		try {
			Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
			System.out.println("Claims--->"+claims.getExpiration());
			
			return Integer.parseInt(claims.getIssuer());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

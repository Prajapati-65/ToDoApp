package com.bridgeit.springToDoApp.Utility.token;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class VerifiedJWT {
	
	private static Logger logger = (Logger) LogManager.getLogger(VerifiedJWT.class);

	private static final String KEY = "application";

	public static int verify(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
			
			claims.getExpiration();
			logger.info("Claims--->"+claims.getExpiration());
			return Integer.parseInt(claims.getIssuer());
			
		} catch (Exception e) {
			
			logger.error("Exception ");
			e.printStackTrace();
			return 0;
		}
	}
}

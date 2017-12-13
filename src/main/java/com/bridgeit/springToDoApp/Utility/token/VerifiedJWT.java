package com.bridgeit.springToDoApp.Utility.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author Om Prajapti
 *
 */
public class VerifiedJWT {
	

	private static final String KEY = "application";

	/**
	 * @param String token
	 * @return integer
	 */
	public static int verify(String token) {
		
		try {
			Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
			claims.getExpiration();
			return Integer.parseInt(claims.getIssuer());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

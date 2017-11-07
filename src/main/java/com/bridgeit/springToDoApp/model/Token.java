package com.bridgeit.springToDoApp.model;

public class Token {

	private String accessToken;
	
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}

}

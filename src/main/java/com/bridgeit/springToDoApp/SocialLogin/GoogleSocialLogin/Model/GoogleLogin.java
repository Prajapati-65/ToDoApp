package com.bridgeit.springToDoApp.SocialLogin.GoogleSocialLogin.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Om Prajapati
 *
 */
public class GoogleLogin {

	
	private static final String CLIENT_ID = "626052471611-25puhd88m0cefvvfc3pjj1g8ihnibhbf.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "kbqL-iIN8RwVmc59mc6sl7F3";
	private static final String REDIRECT_URI = "http://localhost:8080/ToDoApp/googleLogin";
	
	
	private static String googleLoginUrl = "";

	static {
		try {
			googleLoginUrl = "https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&response_type=code" + "&scope=profile email" + "&approval_prompt=force" + "&access_type=offline";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String generateLoginUrl() {
		return googleLoginUrl;
	}

	/**
	 * @param String code
	 * @return String accessToken
	 * @throws IOException
	 */
	public static String getAccessToken(String code) throws IOException {

		String urlParameters = "code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
				+ "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";
		
		URL url = new URL("https://accounts.google.com/o/oauth2/token");
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String googleResponse = "";
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			googleResponse = googleResponse + line;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		String googleAccessToken = objectMapper.readTree(googleResponse).get("access_token").asText();
		return googleAccessToken;
	}

	/**
	 * @param  String googleAccessToken
	 * @return String profile data
	 * @throws IOException
	 */
	public static String getProfileData(String googleAccessToken) throws IOException {
		
		String profileUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + googleAccessToken ;
		URL urlInfo = new URL(profileUrl);
		
		URLConnection connection = urlInfo.openConnection();
		connection.setDoOutput(true);
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String line = "";
		String googleResponse = "";
		while ((line = bufferedReader.readLine()) != null) {
			googleResponse = googleResponse + line;
		}
		return googleResponse;
	}
	
}

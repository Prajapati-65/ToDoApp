package com.bridgeit.springToDoApp.SocialUtility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleLogin {

	private static final String CLIENT_ID = "673450098818-4o5dgkn4341cilavl1hb5g2t6vrqsg69.apps.googleusercontent.com";

	private static final String REDIRECT_URI = "http://localhost:8080/ToDoApp";

	private static final String CLIENT_SECRET = "XStiK16C0hh_QqyUfm9tEeEA";

	private static String googleLoginUrl = "";

	static {
		try {
			googleLoginUrl = "://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URI, "UTF-8") + "&response_type=code" + "&scope=profile email"
					+ "&approval_prompt=force" + "&access_type=offline";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String generateLoginUrl() {
		return googleLoginUrl;
	}

	public static String getAccessToken(String code) {

		String urlParameters = "code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
				+ "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getProfileData(String googleAccessToken) {
		try {
			URL urlInfo = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + googleAccessToken);
			URLConnection connection = urlInfo.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				response = response + line;
			}
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

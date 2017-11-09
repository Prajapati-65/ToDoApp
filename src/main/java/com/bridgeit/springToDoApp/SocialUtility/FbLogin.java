package com.bridgeit.springToDoApp.SocialUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FbLogin {

	private static final String APP_ID = "1845582508804612";
	private static final String APP_SECRET = "3e8b7173d07b4ce5857a466c3ba86b3b";
	private static final String REDIRECT_URI = "http://localhost:8080/ToDoApp";

	private static final String BINDING = "&fields=id,email,first_name,last_name,picture";
	private static String facebookUrl;

	static {
		facebookUrl = "https://www.facebook.com/v2.11/dialog/oauth?client_id=" + APP_ID + "&redirect_uri="
				+ URLEncoder.encode(REDIRECT_URI) + "&scope=email";
	}

	public static String getFbLoginUrl() {
		return facebookUrl;
	}

	public static String getFbAccessToken(String code) {
		String urlParameters = "client_id=" + APP_ID + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI)
				+ "&client_secret=" + APP_SECRET + "&code=" + code;
		try {
			URL url = new URL("https://graph.facebook.com/v2.10/oauth/access_token");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String fbResponse = "";
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				fbResponse = fbResponse + line;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			String fbAccessToken = objectMapper.readTree(fbResponse).get("access_token").asText();
			return fbAccessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getProfileData(String fbAccessToken) {
		String profileUrl = "https://graph.facebook.com/v2.11/oauth/access_token=" + fbAccessToken + BINDING;
		try {
			URL url = new URL(profileUrl);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			String profileData = "";

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				profileData = profileData + line;
			}
			System.out.println(profileData);
			return profileData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}

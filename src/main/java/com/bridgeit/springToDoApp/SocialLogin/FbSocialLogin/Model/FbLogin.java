package com.bridgeit.springToDoApp.SocialLogin.FbSocialLogin.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.bridgeit.springToDoApp.SocialLogin.FbSocialLogin.Controller.FacebookController;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FbLogin {

	static Logger logger = (Logger) LogManager.getLogger(FbLogin.class);

	private static final String APP_ID = "1845582508804612";
	private static final String APP_SECRET = "3e8b7173d07b4ce5857a466c3ba86b3b";
	private static final String REDIRECT_URI = "http://localhost:8080/ToDoApp/facebookLogin";

	private static final String BINDING = "&fields=id,name,email,first_name,last_name,picture";
	private static String facebookUrl;

	static {
		facebookUrl = "https://www.facebook.com/v2.11/dialog/oauth?client_id=" + APP_ID + "&redirect_uri="
				+ URLEncoder.encode(REDIRECT_URI) + "&state=ToDoApp" + "&scope=public_profile,email";
	}

	public static String getFbLoginUrl() {
		logger.info("fb url is : " + facebookUrl);
		return facebookUrl;
	}

	public static String getFbAccessToken(String code) throws IOException {

		String urlParameters = "client_id=" + APP_ID + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI)
				+ "&client_secret=" + APP_SECRET + "&code=" + code;

		URL url = new URL("https://graph.facebook.com/v2.11/oauth/access_token");

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
		logger.info("Access token: " + fbAccessToken);
		return fbAccessToken;
	}

	public static String getProfileData(String fbAccessToken) throws IOException {
		String profileUrl = "https://graph.facebook.com/v2.9/me?access_token="+fbAccessToken+BINDING;
		URL url = new URL(profileUrl);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		logger.info("Connection is :" + connection);
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (Exception E) {
			logger.error("Exceptions..");
		}
		String line = "";
		String profileData = "";
		while ((line = bufferedReader.readLine()) != null) {
			profileData = profileData + line;
		}
		logger.info("Profile data is : "+profileData);
		return profileData;
	}
}

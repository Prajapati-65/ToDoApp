package com.bridgeit.springToDoApp.Utility;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Om Prajapati
 *
 */
public class UrlTemplate {

	/**
	 * @param HttpServletRequest request
	 * @return String
	 */
	public static String urlTemplate(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		URL lUrl = null;
		try {
			lUrl = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		String port = "";
		int p = lUrl.getPort();
		if (p > -1) {
			port = ":" + p;
		}
		url = lUrl.getProtocol() + "://" + lUrl.getHost() + "" + port + "/ToDoApp/";
		return url;
	}
}

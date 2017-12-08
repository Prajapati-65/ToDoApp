package com.bridgeit.springToDoApp.Utility.Jsoup;

public class UrlData {

	private String title;

	private String imageUrl;

	private String domain;

	public UrlData(String title, String imageUrl, String domain) {

		this.title = title;
		this.imageUrl = imageUrl;
		this.domain = domain;
	}

	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}

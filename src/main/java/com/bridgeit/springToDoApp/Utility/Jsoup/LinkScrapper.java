package com.bridgeit.springToDoApp.Utility.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author Om Prajapati
 *
 */
@Component
public class LinkScrapper {

	/**
	 * @param String url
	 * @return UrlData
	 * @throws IOException
	 */
	public UrlData getUrlMetaData(String url) throws IOException {

		String title = null;
		String imageUrl = null;
		String domain = null;

		try {
			URI uri = new URI(url);
			domain = uri.getHost();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Document document = Jsoup.connect(url).get();
		Elements metaOgTitle = document.select("meta[property=og:title]");

		if (metaOgTitle != null) {
			title = metaOgTitle.attr("content");

			if (title == null) {
				title = document.title();
			}
		}

		Elements metaOgImage = document.select("meta[property=og:image]");
		if (metaOgImage != null) {
			imageUrl = metaOgImage.attr("content");
		}
		return new UrlData(title, imageUrl, domain);
	}

}

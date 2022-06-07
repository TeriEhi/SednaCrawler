package sedna_web_crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class SednaWebCrawler {

	private HashSet<String> siteLinks;

	public SednaWebCrawler() {
		siteLinks = new HashSet<String>();
	}

	public void getLinks(String URL) {
		if (!siteLinks.contains(URL)) {
			try {
				if (siteLinks.isEmpty()) {
					System.out.println("Website Link: " + URL);
				} else {
					System.out.println("\nPage Link: " + URL);
				}

				siteLinks.add(URL);

				Document webPage = Jsoup.connect(URL).get();
				Elements pageLinks = webPage.select("a[href]");
				Elements images = webPage.select("img[src~=(?i)\\.(png|jpe?g|gif)]");


				if(!images.isEmpty()) {
					System.out.println("\nStatic Assets");
					for(Element image : images) {
						System.out.println("src : " + image.attr("abs:src"));
					}
				}

				if(!pageLinks.isEmpty()) {
					System.out.println("\nLinks on page");

					for(Element page : pageLinks) {
						//Limit it to one domain
						if(page.attr("abs:href").contains(URL)) {
							System.out.println("Text: " + page.text() + " URL: " + page.attr("abs:href"));
						}

					}


					for(Element page : pageLinks) {
						//Limit it to one domain
						if(page.attr("abs:href").contains(URL)) {
							getLinks(page.attr("abs:href"));
							System.out.println("text : " + page.text());
						}

					}
				}


			} catch (IOException e) {
				System.err.println(URL + " : " + e.getMessage());
			}
		}
	}


	public static void main(String[] args) {

		new SednaWebCrawler().getLinks("https://sedna.com/");//https://teriehi.github.io/CV  https://amazon.co.uk/

	}

}

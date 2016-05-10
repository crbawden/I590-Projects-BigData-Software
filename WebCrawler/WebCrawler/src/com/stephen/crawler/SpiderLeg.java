package com.stephen.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iu.info590.dnoell.utils.Utility;

/* Source: Based on SpiderLeg.java Code found at: 
Net Instructions.  December 18, 2015.  "How to make a simple web crawler in Java"  
Retrieved on April 12, 2016 from http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/.
*/
public class SpiderLeg
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Document htmlDocument;
    private List<String> videoPages = new ArrayList<String>();
	private List<String> searchResultPages = new ArrayList<String>();
	private String watchURL = "https://www.youtube.com/watch";
    private String queryURL ="https://www.youtube.com/results?";
	private Logger logger = Logger.getLogger(SpiderLeg.class);

    public Document getDocument(){
    	return htmlDocument;
    }
    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     * 
     * @param url
     *            - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url, List<String> currentSearchPages, List<String> currentVideoPages)
    {
    	String newURL = "";
        try
        {
        	Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() != 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                logger.error("**Failure** Status code received: " + connection.response().statusCode());
                return false;
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                logger.error("**Failure** Retrieved something other than HTML");
                return false;
            }
            
            Elements linksOnPage = htmlDocument.select("a[href]");
            for(Element link : linksOnPage)
            {
            	newURL = link.absUrl("href");
            	if(Utility.containsCaseInsensitive(newURL, watchURL)){
            		if(!Utility.equalsCaseInsensitive(newURL, this.videoPages) 
            				&& !Utility.equalsCaseInsensitive(newURL, currentVideoPages)){
            			this.videoPages.add(newURL);
            			logger.debug("Video page: " + newURL);
            		}
            	}
            	else if(Utility.containsCaseInsensitive(newURL, queryURL) 
            			&& Utility.containsCaseInsensitive(newURL, "page=")){
            		
            		String searchURL = Utility.normalizeSearchURL(newURL);
            		if(!Utility.equalsCaseInsensitive(searchURL, this.searchResultPages) 
            				&& !Utility.equalsCaseInsensitive(searchURL, currentSearchPages)){
            			this.searchResultPages.add(searchURL);
            			logger.debug("Search page: " + searchURL);
            		}
            	}	
            }
            return true;
        }
        catch(IOException ioe)
        {
        	logger.error(ioe.getMessage(),ioe.getCause());
        	
        	//System.out.println(ioe.toString());
            // We were not successful in our HTTP request
            return false;
        }
    }
   
    public Document getHLMTDocument(String url){
    	Document htmlDocument = null;
    	try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            htmlDocument = connection.get();
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
        	logger.error(ioe.getMessage(),ioe.getCause());
            return null;
        }
    	
    	return htmlDocument;
            
    }

    public List<String> getSearchResultPages()
    {
        return this.searchResultPages;
    }    
    public List<String> getVideoPages()
    {
        return this.videoPages;
    }
}

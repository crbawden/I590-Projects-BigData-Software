package com.iu.info590.dnoell.pagedata;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.stephen.crawler.SpiderLeg;

/* Source: Class adapted to use multi-threading based on the following: 
Particle In Cell Consulting, LLC.  June 25th, 2011.  "Get results faster with Java multithreading: 
Parallel version simulation.java."  Retrieved on April 12, 2016 from https://www.particleincell.com/2011/java-multithreading/.
*/
public class GatherPageData  extends Thread{
	private Logger logger = Logger.getLogger(GatherPageData.class);
	private String pageUrl = "";
    private PageData pd = new PageData();
    private int count = 0;
    private String searchString = "";
    
    public PageData getPageData(){
    	return pd;
    }
 
    public GatherPageData (int count, String pageUrl, String searchString, String name,  ThreadGroup tg)
    {
        super(tg, name);
        this.pageUrl = pageUrl;
        this.count = count;
        this.searchString = searchString;
    }
 
    public void run()
    {
    	try {
    		getPageDataFromURL();
		} catch (IOException e) {
			logger.error(e.getMessage(), e.getCause());
		}
    }
    
    public void getPageDataFromURL() throws IOException{
    	
    	logger.debug((count + 1) + ". Getting data from Video Pages: " + pageUrl);
		//Gets the data from the videoPage
		SpiderLeg leg = new SpiderLeg();
		Document httpPage = leg.getHLMTDocument(pageUrl);
		if(httpPage != null){		
			pd.capturePageData(pageUrl, this.searchString, httpPage);
		}
	}
}

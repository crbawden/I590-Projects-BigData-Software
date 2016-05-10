package com.stephen.crawler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.iu.info590.dnoell.pagedata.PageData;
import com.iu.info590.dnoell.pagedata.PageDataList;
import com.iu.info590.dnoell.utils.Constants;
import com.iu.info590.dnoell.utils.OutputResults;

/* Source: Based on Spider.java Code found at: 
Net Instructions.  December 18, 2015.  "How to make a simple web crawler in Java"  
Retrieved on April 12, 2016 from http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/.
*/
public class Spider
{
	private Logger logger = Logger.getLogger(Spider.class);
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new ArrayList<String>();
	private List<String> searchPages = new ArrayList<String>();
	private List<String> videoPages = new ArrayList<String>();
  
	public void getPages(String searchString, String url, String path, String outputFileName, 
			  long maxPagesToReturn) throws InterruptedException
	  {
			boolean outputToFile = false;
			if(!"".equals(outputFileName.trim()))
				outputToFile = true;
			
			logger.debug("Initial URL: " + url);		  
		  
			this.pagesToVisit.add(url);	  
		  
			while(videoPages.size() < maxPagesToReturn && this.pagesToVisit.size() != 0)
			{
				String currentUrl;
				SpiderLeg leg = new SpiderLeg();
				if(this.pagesToVisit.isEmpty())
				{
					currentUrl = url;
					this.pagesVisited.add(url);
				}
				else
				{
					currentUrl = this.nextUrl();
				}	          
	          
				leg.crawl(currentUrl, this.searchPages, this.videoPages); // Lots of stuff happening here. Look at the crawl method in SpiderLeg	 
	          
				if(leg.getSearchResultPages() !=null){
					this.searchPages.addAll(leg.getSearchResultPages());
					this.pagesToVisit.addAll(leg.getSearchResultPages());
				}
	          
				if(leg.getVideoPages() != null){
					this.videoPages.addAll(leg.getVideoPages());
					logger.trace(videoPages.size() + " video pages gathered.");
				}
		  }	
	      
	      //Gathers the data for each page
	      PageDataList pageDataList =  new PageDataList();
	      Long pagesToReturn = 0L;
	      if(maxPagesToReturn > videoPages.size())
	    	  pagesToReturn = (long)videoPages.size();
	      else
	    	  pagesToReturn = maxPagesToReturn;
	      
	      List<PageData> pdList = pageDataList.createPageDataList(searchString, videoPages, pagesToReturn);
	      
	      OutputResults or = new OutputResults();
	      if(outputToFile){
	    	  //Output results to a Text file	    	 
	    	  try {
				or.outputToFile(pdList, path, outputFileName, pagesToReturn);
	    	  } catch (IOException e) {
				logger.error(e.getMessage(), e.getCause());
	    	  }
	      }
	      else {
	    	 
	    	  try {
		    	 if("".equals(Constants.ssh_host.trim()))
		    		 or.outputToMongoDb(pdList, pagesToReturn);
		    	 else
		    		 or.outputToSSHMongoDb(pdList, pagesToReturn);    		  
	    	  } catch (UnknownHostException e) {
				logger.error(e.getMessage(), e.getCause());
	    	  }
	      }
	 
	  }

	  /**
	   * Returns the next URL to visit (in the order that they were found). We also do a check to make
	   * sure this method doesn't return a URL that has already been visited.
	   * 
	   * @return
	   */
	  private String nextUrl()
	  {
		  String nextUrl;
		  do
		  {
			  nextUrl = this.pagesToVisit.remove(0);
		  } while(this.pagesVisited.contains(nextUrl));    
		  this.pagesVisited.add(nextUrl);
		  return nextUrl;
	  }
}

package com.iu.info590.dnoell.pagedata;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/* Source: Class created to use multi-threading based on the following: 
Particle In Cell Consulting, LLC.  June 25th, 2011.  "Get results faster with Java multithreading: 
Parallel version main.java."  Retrieved on April 12, 2016 from https://www.particleincell.com/2011/java-multithreading/.
*/

public class PageDataList {
	private Logger logger = Logger.getLogger(PageDataList.class);

	public List<PageData> createPageDataList(String searchString, List<String> urls, Long pagesToReturn){
		List<PageData> results = new ArrayList<PageData>();
		//Sets up the thread
		ThreadGroup tg = new ThreadGroup("getVideoPages");
		long start = System.nanoTime();
		int i = 0;
		int np = Runtime.getRuntime().availableProcessors();
		
		//Gets data from each page and outputs to a file
		List<GatherPageData> gPageDataList = new ArrayList<GatherPageData>();
		for(i = 0; i < pagesToReturn ; i++){ // videoUrl: videoPages){
		  gPageDataList.add(new GatherPageData(i, urls.get(i), searchString, "GetVideoPageData-" + i, tg));
		}
		i=0;
		while (i < pagesToReturn)
		{
		  /*do we have available CPUs?*/
		  if (tg.activeCount() < np)
		  {
			  GatherPageData gPageData = gPageDataList.get(i);
			  gPageData.start();
			  i++;
		  } else
			  	try {Thread.sleep(100);} /*wait 0.1 second before checking again*/
					catch (InterruptedException e) {
						//e.printStackTrace();
						logger.error(e.getMessage(),e.getCause());
					}
		}
		
		/*wait for threads to finish*/
		while(tg.activeCount()>0)
		{
			try {Thread.sleep(100);} 
				catch (InterruptedException e) {
					//e.printStackTrace();
					logger.error(e.getMessage(),e.getCause());
				}
		}
		
		
		for (i=0; i < gPageDataList.size();i++)
		{
		  GatherPageData gPageData = gPageDataList.get(i);
		  results.add(gPageData.getPageData());
		}
		
		long end  = System.nanoTime();
	     
		logger.debug("Records Processed: " + results.size());
		logger.debug("OutputResults took %.2g secondsn: " + (double)(end-start)/1e9);

		
		return results;
	}
}

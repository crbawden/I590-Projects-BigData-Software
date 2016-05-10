package com.iu.info590.dnoell.capture;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.iu.info590.dnoell.utils.Constants;
import com.iu.info590.dnoell.utils.Timings;
import com.iu.info590.dnoell.utils.Utility;
import com.stephen.crawler.Spider;

public class CaptureUrlData
{
	private static Logger logger = Logger.getLogger(CaptureUrlData.class);
    
    public static void main(String[] args) throws IOException
    {   
    	//PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure(Constants.filesDir + "/log4j.properties");
        
    	//Get data from search
        try {
	    	String searchString = args[0];
	    	String filter = args[1];
	    	int pagesToCrawl = Integer.parseInt(args[2]);
	    	
	    	String intitialSearchURL = Utility.buildInitialSerchURL(Constants.baseURL, searchString, filter);	    	    	
	        Spider spider = new Spider();     
	        
	        //Instantiates the Timings class
	        Timings timings = new Timings();
	        
	        //Sets the Start Time
	        timings.setStartTime(Calendar.getInstance());
	        
	        //Prints the Start Time
	        timings.printStartTime();
	        
	        //Output info for logs
	        logger.info("     Search String: " + searchString);
	        logger.info("     Filter: " + filter);
	        logger.info("     Records to find: " + pagesToCrawl);
	        
	        spider.getPages(searchString, intitialSearchURL, Constants.filesDir, "", pagesToCrawl);
	        
			 //Sets the End Time
	        timings.setEndTime(Calendar.getInstance());
	        
	        //Prints the End Time
	        timings.printEndTime();
	        
	        //Calculate Time Elapsed
	        timings.calculateTimeElapsed();
	        
	        //Print Elapsed Time
	        timings.printTimeElapsed();
	        
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
        
    }
}

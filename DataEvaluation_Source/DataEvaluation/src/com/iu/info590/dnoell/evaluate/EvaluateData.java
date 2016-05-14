package com.iu.info590.dnoell.evaluate;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.iu.info590.dnoell.utils.Constants;
import com.iu.info590.dnoell.utils.Timings;

public class EvaluateData
{
	private static Logger logger = Logger.getLogger(EvaluateData.class);
    
    public static void main(String[] args) throws IOException
    {   
    	//PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure(Constants.filesDir + "/log4j.properties");
        
    	//Get data from search
        try { 
	        
	        //Instantiates the Timings class
	        Timings timings = new Timings();
	        
	        //Sets the Start Time
	        timings.setStartTime(Calendar.getInstance());
	        
	        //Prints the Start Time
	        timings.printStartTime();
	        
	        //Retrieve data from MongoDB
	        ProcessData rd = new ProcessData();
		   
	        try {
	        	if("".equals(Constants.ssh_host.trim()))
	        		rd.getDataFromMongoDb();
	        	else
					rd.getDataFromSSHMongoDb();   		  
	        } catch (Exception e) {
				logger.error(e.getMessage(), e.getCause());
	        }
		      
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

package com.iu.info590.dnoell.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.iu.info590.dnoell.capture.CaptureUrlData;

public class Timings {
	private static Logger logger = Logger.getLogger(CaptureUrlData.class);

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS");
	private Calendar startTime = null;
	private Calendar endTime = null;
	private Long timeElapsed = 0L;
	
	//Start Time
	public void setStartTime(Calendar val){
		startTime = val;
	}
	public Calendar getStartTime(){
		return startTime;
	}
	public String getStartTimeStr(){
		return dateFormatter.format(startTime.getTime());
	}
	//End Time
	public void setEndTime(Calendar val){
		endTime = val;
	}
	public Calendar getEndTime(){
		return endTime;
	}
	public String getEndTimeStr(){
		return dateFormatter.format(endTime.getTime());
	}
	//Elapsed Time
	public void calculateTimeElapsed(){
		timeElapsed = endTime.getTimeInMillis() - startTime.getTimeInMillis();
	}
	public void setTimeElapsed(Calendar end, Calendar start){
		timeElapsed = end.getTimeInMillis() - start.getTimeInMillis();
	}
	public long getTimeElapsed(){
		return timeElapsed;
	}	
	public String getTimeElapsedStr(){
		return Utility.formatInterval(timeElapsed);
	}
    
    public void printStartTime(){
    	logger.info("Job Start Time: " + getStartTimeStr() );
    }
    public void printEndTime(){
    	logger.info("Job Ends Time: " + getEndTimeStr() );
    }
    public void printTimeElapsed(){
    	logger.info("Total Job Time: " + getTimeElapsedStr() );
    }
}

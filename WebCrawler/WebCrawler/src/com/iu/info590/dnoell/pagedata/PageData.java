package com.iu.info590.dnoell.pagedata;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iu.info590.dnoell.utils.Utility;

public class PageData {
	
	private Logger logger = Logger.getLogger(PageData.class);
	
	/*
	 EXAMPLE DATA
	- Title: Camtasia 2.10.5 Full
	- URL: https://www.youtube.com/watch?v=_tKrZcYkgJY
	- Description: Hola a todos aquí esta el nuevo tutorial de como descargar e instalar Camtasia 2.10.4 Pro. ----Nuevos Links de Camtasia 2.10.5 Pro -------- LINKS debajo... 1.- http://alfafile.net/file/cZ4t 2.- http://uploaded.net/file/h214tza2 3.- http://nitroflare.com/view/25D652BA12... 4.- http://dfiles.eu/files/7wk8fijsj 5.- http://turbobit.net/2wk5x33nc9um.html 6.- http://rapidgator.net/file/cb1108454e...
	- DATE: Published on Dec 7, 2014
	- Views: 16,478
	- User: Madara`
	 */
	private String title = "";
	private String userName = "";
	private String url = "";
	private String description = "";
	private String pubDate = "";
	private Integer views = 0;
	private Integer subscribers = 0;
	private String serialNumber = "";
	private String searchString = "";
	
	public void setTitle(String val){
		title = val;
	}
	public String getTitle(){
		return title;
	}
	public void setUserName(String val){
		userName = val;
	}
	public String getUserName(){
		return userName;
	}
	public void setUrl(String val){
		url = val;
	}
	public String getUrl(){
		return url;
	}
	
	public void setDescription(String val){
		description = val;
	}
	public String getDescription(){
		return description;
	}
	
	public void setPubDate(String val){
		pubDate = val;
	}
	public String getPubDate(){
		return pubDate;
	}
	
	public void setViews(Integer val){
		views = val;
	}
	public Integer getViews(){
		return views;
	}
	public void setSubscribers(Integer val){
		subscribers = val;
	}
	public Integer getSubscribers(){
		return subscribers;
	}
	public void setSerialNumber(String val){
		serialNumber = val;
	}
	public String getSerialNumber(){
		return serialNumber;
	}
	public void setSearchString(String val){
		searchString = val;
	}
	public String getSearchString(){
		return searchString;
	}
	
	
	public boolean capturePageData(String pgUrl, String searchString, Document htmlDocument){
    	// Defensive coding. This method should only be used after a successful crawl.
        if(htmlDocument == null)
        {
        	logger.error("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        //searchString
        if(!"".equals(searchString.trim()))
        	this.searchString = searchString;
        
        //Page Title
        Element	pgTitle = htmlDocument.body().getElementsByClass("watch-title").first();
        if(pgTitle  != null){
        	title = pgTitle.text();
        }
        else{
        	title = "";
        }
        
        //Page UserName
        Element pgUserName = htmlDocument.body().getElementById("watch7-user-header");
        if(pgUserName != null){
        	String val = pgUserName.text();
        	int userLen = pgUserName.text().indexOf("Subscribe") - 1;
        	val = val.substring(0, userLen);
        	userName = val;
        } 
        else {
        	userName = "";
        }
        
        //Page URL
        url = pgUrl;
        
        //Page Description
        Element	pgDesc = htmlDocument.body().getElementById("eow-description");
        if(pgDesc != null){
        	description = pgDesc.text();
        	//description = description.replaceAll( "\\D" , "" );
        	description = description.replaceAll("[^A-Za-z0-9 ]", "");
        }
        else {
        	description = "";
        }
        
        //Serial Number
        if(!"".equals(description)){
        	//Based on: http://www.tutorialspoint.com/java/java_regular_expressions.htm
        	String pattern = "^(:?[a-zA-Z0-9]{5}-){4}[a-zA-Z0-9]{5}$";
        	Pattern r = Pattern.compile(pattern);
        	Matcher m = r.matcher(description);
        	if (m.find( )) {
        		serialNumber = m.group(0);
            }
        }     
        
        //Publish Date
        Element	pgPubDate = htmlDocument.body().getElementsByClass("watch-time-text").first();
        if(pgPubDate  != null){
        	pubDate = pgPubDate.text();
        	pubDate = pubDate.replace("Published on ", "");
        }
        else {
        	pubDate = "";
        }
        
        //Page Views
        Element	pgViews = htmlDocument.body().getElementsByClass("watch-view-count").first();
        if(pgViews  != null){
        	String vw = pgViews.text().trim().replace(",", "");
        	vw = vw.trim().replace("views", "");
        	vw = vw.trim().replace(" ", "");
        	if(Utility.isNumeric(vw))
        		views = Integer.parseInt(vw);
        	else
        		views = 0;
        }  
        else {
        	views = 0;
        }
        
        //User Subscribers
        Element pgSubscribers = htmlDocument.select("span[class=yt-subscription-button-subscriber-count-branded-horizontal yt-subscriber-count]").first();
        if(pgSubscribers != null){
        	String subs = pgSubscribers.text().trim().replace(",", "");
        	if(Utility.isNumeric(subs))
        		subscribers = Integer.parseInt(subs);
        	else
        		subscribers = 0;
        	
        }
        else {
        	subscribers = 0;
        }
        
        return true;
    }
}

package com.iu.info590.dnoell.entities;

public class PageData {
	
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
	
}

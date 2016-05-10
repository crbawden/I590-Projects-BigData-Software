package com.iu.info590.dnoell.utils;

public final class Constants {
	public static String filesDir = "resources";
	public static String baseURL = "https://www.youtube.com/results";
	public static String propertiesFile = "WebCrawler.properties";

	public static String databaseName = Configuration.getInstance().getProperty("databaseName").trim();
	public static String collectionName = Configuration.getInstance().getProperty("collectionName").trim();	
	public static String outputFileName = Configuration.getInstance().getProperty("outputFileName").trim();
		
	public static String local_host = Configuration.getInstance().getProperty("local_host").trim(); 
	public static String remote_host = Configuration.getInstance().getProperty("remote_host").trim();
	public static int local_port = Integer.parseInt(Configuration.getInstance().getProperty("local_port").trim());
	public static int remote_port = Integer.parseInt(Configuration.getInstance().getProperty("remote_port").trim()); 
	public static String priv_key_path = Configuration.getInstance().getProperty("priv_key_path").trim();
	public static String priv_key_pass_phrase = Configuration.getInstance().getProperty("priv_key_pass_phrase").trim();
	public static String ssh_user = Configuration.getInstance().getProperty("ssh_user").trim(); 
	public static String ssh_password = Configuration.getInstance().getProperty("ssh_password").trim();
	public static String ssh_host = Configuration.getInstance().getProperty("ssh_host").trim();
	public static int ssh_port = Integer.parseInt(Configuration.getInstance().getProperty("ssh_port").trim());

}

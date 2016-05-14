package com.iu.info590.dnoell.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;

import com.iu.info590.dnoell.evaluate.EvaluateData;

public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);

	public static String buildInitialSerchURL(String baseUrl, String searchString, String filter){
		String result = "";
		result = baseUrl;
		if(!"".equalsIgnoreCase(searchString.trim()))
				result += "?q=" + searchString.trim();
		
		if(!"".equalsIgnoreCase(filter.trim()))
				result += "&sp=" + filter.trim();
		return result;
		
	}
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	//Source: http://stackoverflow.com/questions/15269723/array-contains-without-case-sensitive-lookup
    public static boolean containsCaseInsensitive(String s, List<String> l){
        for (String string : l){
           if (s.toLowerCase().contains(string.toLowerCase())){
               return true;
            }
        }
       return false;
     }
    
    public static boolean equalsCaseInsensitive(String s, List<String> l){
        for (String string : l){
           if (s.toLowerCase().equals(string.toLowerCase())){
               return true;
            }
        }
       return false;
     }
    
    public static boolean containsInteger(Integer s, List<Integer> I){
        for (Integer integer : I){
           if (I.contains(integer)){
               return true;
            }
        }
       return false;
     }
    
    //http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    public static boolean isNumericValue(String val){
    	boolean result = Stream.of(val)
                .filter(s -> s != null && !s.isEmpty())
                .filter(Pattern.compile("\\D").asPredicate().negate())
                .mapToLong(Long::valueOf).boxed().findAny()
                .isPresent();
    	
    	return result;
    }
    public static boolean containsCaseInsensitive(String s, String l){
	   if (s.toLowerCase().contains(l.toLowerCase())){
	       return true;
	    }
       return false;
     }
        
    public static String getOutputPath(String path){
    	String result =  "";
    	
    	try {
    		if("".equals(path.trim())){
		        result = getJarPath();
    		}
    		else
    			result = path.trim();
	        
	    } catch (Exception e1) {
	        logger.error(e1.getMessage(),e1.getCause());
	    }
    	
    	return result;
    }
    
    public static String getJarPath(){
    	String result =  "";
    	
    	try {
	        File jarPath=new File(EvaluateData.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String path = jarPath.getParentFile().getAbsolutePath();
	        path = path.replace("%20", " ");
	        File f = new File(path + "/bin");
	        if(f.exists()  && f.isDirectory())
	        	path = path + "/bin";
	        
	        result = path;
	        
	    } catch (Exception e1) {
	        logger.error(e1.getMessage(),e1.getCause());
	    }
    	
    	return result;
    }
    
    public static void createFile(String path, String fileName) throws IOException{
    	// Date Format - Source: http://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	Calendar cal = Calendar.getInstance();
    	//System.out.println(dateFormat.format(cal.getTime())); 
    	
    	File f = new File(path + "/" + fileName);   	
    	if(f.exists()){
    		//Rename file
    		//Source: http://blog-en.openalfa.com/how-to-rename-move-or-copy-a-file-in-java
    		File newFileName = new File(path + "/" + fileName + "_" + dateFormat.format(cal.getTime()));
    		f.renameTo(newFileName);
    	}
    	//Create blank file
		f.createNewFile();
    }
    
    //Source: http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
	public static void appendStringToFile(String text, String fileName )
	{
	    FileWriter fileWriter;
	    try {
	        fileWriter = new FileWriter(fileName,true);
	        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
	        bufferFileWriter.append(text);
	        bufferFileWriter.newLine();
	        bufferFileWriter.close();
	    } catch (IOException ex) {
	    	logger.error(ex.getMessage(),ex.getCause());
	    }
	}
	//Source: http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
	public static void appendUrlDataToFile(String urlOutputFormat, String counter, String url, String title, 
			String userName, String totalScore, String views, String fileName, Boolean header)	
	{
	    FileWriter fileWriter;
	    try {
	        fileWriter = new FileWriter(fileName,true);
	        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
	        if(!header)
	        	logger.debug(counter + ". " + url + " - " + title + " - " + userName + " - " + totalScore + " - " + views);
	        bufferFileWriter.append(String.format(urlOutputFormat, counter, url, title, userName, totalScore, views));
	        bufferFileWriter.newLine();
	        bufferFileWriter.close();
	    } catch (IOException ex) {
	    	logger.error(ex.getMessage(),ex.getCause());
	    }
	}
	
	//Source: http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
		public static void appendUserNameDataToFile(String urlOutputFormat, String counter, String userName, String totalScore, String views, String fileName, Boolean header)	
		{
		    FileWriter fileWriter;
		    try {
		        fileWriter = new FileWriter(fileName,true);
		        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
		        if(!header)
		        	logger.debug(counter + ". " + userName + " - " + totalScore + " - " + views);
		        bufferFileWriter.append(String.format(urlOutputFormat, counter, userName, totalScore, views));
		        bufferFileWriter.newLine();
		        bufferFileWriter.close();
		    } catch (IOException ex) {
		    	logger.error(ex.getMessage(),ex.getCause());
		    }
		}
	
	
	//Source: http://stackoverflow.com/questions/6710094/how-to-format-an-elapsed-time-interval-in-hhmmss-sss-format-in-java
	public static String formatInterval(final long l)
    {
        final long hr = TimeUnit.MILLISECONDS.toHours(l);
        final long min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
    }
	
	
	//Based on: http://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection
	public static String normalizeSearchURL(String url)  {
		String result = "";
		String sp = "";
		String search_query = "";
		String page = "";
		try {	
				List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
				URL aURL = new URL(url);
				String baseURL = aURL.getProtocol() + "://" + aURL.getHost() + aURL.getPath() + "?";
							
				for (NameValuePair param : params) {
					switch(param.getName()){
						case "sp": sp = param.getValue().trim();
							break;
						case "search_query": search_query = URLEncoder.encode(param.getValue().trim(), "UTF-8");
							break;
						case "q": search_query = param.getValue().trim();
							break;
						case "page": page = param.getValue().trim();
							break;
					}
				}
				
				result = baseURL;
				if(!"".equals(sp.trim()))
					result += "sp="+sp;
				
				if(!"".equals(search_query.trim()) && !"".equals(sp.trim()))
					result += "&search_query="+search_query;
				else if (!"".equals(search_query.trim()))
					result += "search_query="+search_query;
				
				if(!"".equals(page.trim()) && (!"".equals(sp.trim()) || !"".equals(search_query.trim())))
					result += "&page="+ page;
				else if(!"".equals(page.trim()))
					result += "page="+ page;
			
			
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(),e.getCause());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(),e.getCause());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e.getCause());
		}
		
		return result;
	}
}

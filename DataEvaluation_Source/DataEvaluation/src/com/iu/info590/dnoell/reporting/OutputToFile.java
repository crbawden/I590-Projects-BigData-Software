package com.iu.info590.dnoell.reporting;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.iu.info590.dnoell.entities.DataScore;
import com.iu.info590.dnoell.utils.Constants;
import com.iu.info590.dnoell.utils.Utility;

public class OutputToFile {
	private static Logger logger = Logger.getLogger(OutputToFile.class);
	
	private static Integer urlSize = 60;
	private static Integer titleSize = 120;
	private static Integer userNameSize = 80;
	private static Integer totalScoreSize = 20;
	private static Integer viewsSize = 20;
	
	public static void outputUrlData(List<DataScore> dsList, String filePath, int widthOfMax) {
		Integer counter = 0;
		String separator = System.getProperty("line.separator");
		logger.debug("Exporting records to data file for URL Scores");
		
		
		
		try{
			//Report Header
			Utility.appendStringToFile("Url Data Scores Based on Total Score and number of Views per Page", filePath);
			Utility.appendStringToFile(String.join("", Collections.nCopies(80, "_")), filePath);
			Utility.appendStringToFile(separator, filePath);
	
			//Insert Section Header
			StringBuilder sb = new StringBuilder();
			sb.append("%1$" + widthOfMax + "s");
			sb.append(" ");
			sb.append("%2$" + urlSize + "s");
			sb.append(" ");
			sb.append("%3$" + titleSize + "s");
			sb.append(" ");
			sb.append("%4$" + userNameSize + "s");
			sb.append(" ");
			sb.append("%5$" + totalScoreSize + "s");
			sb.append(" ");
			sb.append("%6$" + viewsSize + "s");
			
			String urlOutputFormat = sb.toString();
			
			//Append Header
			Utility.appendUrlDataToFile(urlOutputFormat, "#", "URL", "TITLE", "USER NAME", 
					"SCORE", "VIEWS", filePath, true);
			Utility.appendUrlDataToFile(urlOutputFormat, 
					String.join("", Collections.nCopies(widthOfMax, "_")), 
					String.join("", Collections.nCopies(urlSize, "_")), 
					String.join("", Collections.nCopies(titleSize, "_")), 
					String.join("", Collections.nCopies(userNameSize, "_")), 
					String.join("", Collections.nCopies(totalScoreSize, "_")), 
					String.join("", Collections.nCopies(viewsSize, "_")), filePath, true);
			
			//Append URL Data
			for(DataScore ds : dsList){
				if(Utility.isNumeric(ds.getTotalScore().toString().trim()) && Utility.isNumeric(ds.getViews().toString().trim())) {
					counter++;
					String url = ds.getUrl();
					String title = "";
					if(ds.getTitle().length() > titleSize-5)
						title = ds.getTitle().substring(0, titleSize-5).replaceAll("[^\\p{ASCII}]", "");
					else
						title = ds.getTitle().replaceAll("[^\\p{ASCII}]", "");
					String userName = "";
					if(ds.getUserName().length() > userNameSize-5)
						userName = ds.getUserName().substring(0, userNameSize-5).replaceAll("[^\\p{ASCII}]", "");
					else
						userName = ds.getUserName().replaceAll("[^\\p{ASCII}]", "");
					String totalScore = ds.getTotalScore().toString().replaceAll("[^\\p{ASCII}]", "");
					String views = ds.getViews().toString().replaceAll("[^\\p{ASCII}]", "");
					
					Utility.appendUrlDataToFile(urlOutputFormat, counter.toString(), url, title, userName, 
							totalScore, views, filePath,false);
					
					if(counter == Constants.maxRecordsOut)
						break;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	public static void outputUserNameData(List<DataScore> dsList, String filePath, int widthOfMax) {
		Integer counter = 0;
		String separator = System.getProperty("line.separator");
		logger.debug("Exporting records to data file for UserName Scores");
		try{
			//Report Header
			Utility.appendStringToFile("User Name Data Scores Based on Aggregate of Total Score and number of Views per Page", filePath);
			Utility.appendStringToFile(String.join("", Collections.nCopies(80, "_")), filePath);
			Utility.appendStringToFile(separator, filePath);
			
			//Formatting
			StringBuilder sb = new StringBuilder();
			sb.append("%1$" + widthOfMax + "s");
			sb.append(" ");
			sb.append("%2$" + userNameSize + "s");
			sb.append(" ");
			sb.append("%3$" + totalScoreSize + "s");
			sb.append(" ");
			sb.append("%4$" + viewsSize + "s");
			
			//Insert Section Header
			String userNameOutputFormat = sb.toString();
			Utility.appendUserNameDataToFile(userNameOutputFormat, "#", "USER NAME", "SCORE", 
					"VIEWS", filePath, true);
			Utility.appendUserNameDataToFile(userNameOutputFormat, 
					String.join("", Collections.nCopies(widthOfMax, "_")), 
					String.join("", Collections.nCopies(userNameSize, "_")), 
					String.join("", Collections.nCopies(totalScoreSize, "_")), 
					String.join("", Collections.nCopies(viewsSize, "_")), filePath, true);
			
			//Append Data to file
			for(DataScore ds : dsList){
				if(Utility.isNumeric(ds.getTotalScore().toString().trim()) && Utility.isNumeric(ds.getViews().toString().trim())) {
					counter++;
					String userName = "";
					if(ds.getUserName().length() > userNameSize-5)
						userName = ds.getUserName().substring(0, userNameSize-5).replaceAll("[^\\p{ASCII}]", "");
					else
						userName = ds.getUserName().replaceAll("[^\\p{ASCII}]", "");
					String totalScore = ds.getTotalScore().toString().replaceAll("[^\\p{ASCII}]", "");
					String views = ds.getViews().toString().replaceAll("[^\\p{ASCII}]", "");
					
					Utility.appendUserNameDataToFile(userNameOutputFormat, counter.toString(), userName, totalScore, views, filePath, false);
					
					if(counter == Constants.maxRecordsOut)
						break;
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	
	
	
}


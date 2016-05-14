package com.iu.info590.dnoell.evaluate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.iu.info590.dnoell.db.DatabaseCRUD;
import com.iu.info590.dnoell.entities.DataScore;
import com.iu.info590.dnoell.entities.PageData;
import com.iu.info590.dnoell.reporting.OutputToFile;
import com.iu.info590.dnoell.utils.Constants;
import com.iu.info590.dnoell.utils.Utility;
import com.iu.info590.dnoell.utils.WordCount;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ProcessData {
	private static Logger logger = Logger.getLogger(ProcessData.class);
	private String outputFileName = "DataEvaluation.results";
	private String filePath = Constants.filesDir + "/" + outputFileName;
	private String separator = System.getProperty("line.separator");
	//Source: http://stackoverflow.com/questions/1306727/way-to-get-number-of-digits-in-an-int
	private int widthOfMax = 3 + (int)Math.floor(Math.log10(Constants.maxRecordsOut));
	
	public void getDataFromMongoDb() {		
		
		try {
			logger.debug("MongoDB: Preparing to Insert in database");
			
			@SuppressWarnings("resource")
			//Connect to Mongo Database
			MongoClient mongoClient = new MongoClient(Constants.local_host, Constants.local_port); 	
			MongoDatabase mongoDB = mongoClient.getDatabase(Constants.databaseName);
		    		  
		    DatabaseCRUD crud = new DatabaseCRUD();
		    
		    //Get Search Data from MongoDB
		    MongoCollection<Document> searchDataCollection = mongoDB.getCollection(Constants.collectionName);
		    List<PageData> rawDataList = crud.retrieveSearchData(searchDataCollection);		                
            
		    //Output Page Results
			outPutData(rawDataList); 
            
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}

	public void getDataFromSSHMongoDb() { 
		
		Session session = null;
		List<PageData> rawDataList = null;
		try{
			//SSH with Private Key adapted from http://www.jcraft.com/jsch/examples/UserAuthPubKey.java.html
			//JSch - Examples - UserAuthPubK
			//JCraft
			//Retrieved May 4, 2016
			//
			//SSH Connection with MongoClient Adapted from http://yuluer.com/page/dbbgbcaj-connecting-to-mongo-database-through-ssh-tunnel-in-java.shtml
			//YuRuer.com
			//Published in 2015-07-01 12:23:36Z
			//Retrieved May 4, 2016
			
			JSch jsch=new JSch();
			jsch.addIdentity(Constants.priv_key_path, Constants.priv_key_pass_phrase);
	
			session = jsch.getSession(Constants.ssh_user, Constants.ssh_host, Constants.ssh_port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			session.setPortForwardingL(Constants.local_port, Constants.remote_host, Constants.remote_port); 
	
			@SuppressWarnings("resource")
			//Connect to Mongo Database
			MongoClient mongoClient = new MongoClient(Constants.local_host, Constants.local_port); 	
			MongoDatabase mongoDB = mongoClient.getDatabase(Constants.databaseName);
		    		  
		    DatabaseCRUD crud = new DatabaseCRUD();
		    
		    //Get Search Data from MongoDB
		    MongoCollection<Document> searchDataCollection = mongoDB.getCollection(Constants.collectionName);
		    rawDataList = crud.retrieveSearchData(searchDataCollection);
		    
		}
		catch(Exception e){
			logger.error(e.getMessage(), e.getCause());
		}
		finally{
			try {
				session.delPortForwardingL(Constants.local_port);
			} catch (JSchException e) {
				logger.error(e.getMessage(), e.getCause());
			} 
			session.disconnect(); 
		}
		
		try{
			//Output Page Results
			outPutData(rawDataList);            
		} catch(Exception e){
			logger.error(e.getMessage(), e.getCause());
		}
	}
	private void outPutData(List<PageData> pdList)
	{
		if(pdList != null){
			try{
			//Create new file
			Utility.createFile(Constants.filesDir, outputFileName);
			
			//Output Url Results
		    List<DataScore> urlList = scoreAndSortByTotalScore(pdList);
            OutputToFile.outputUrlData(urlList, filePath, widthOfMax);
            
            //Add Spacer between reports
            Utility.appendStringToFile(separator, filePath);
            Utility.appendStringToFile(separator, filePath);
            
            //Output User Name Results
		  	List<DataScore> userNameList = aggregateByUserName(urlList);
            OutputToFile.outputUserNameData(userNameList, filePath, widthOfMax);
			} catch (Exception e) {
				logger.error(e.getMessage(), e.getCause());
			}
		}
	}
	
	private List<DataScore> scoreAndSortByTotalScore(List<PageData> pdList){
		List<DataScore> result = new ArrayList<DataScore>();
	    for (PageData pd : pdList){
	    	DataScore ds = WordCount.getUrlScores(pd);
	    	result.add(ds);
	    }
	    
	    //Sorts Data by Total Score
	    Collections.sort(result, DataScore.TotalScoreComparitor);
	    
	    return result;
	}
	
	private List<DataScore> aggregateByUserName(List<DataScore> dsList){
		List<DataScore> result = new ArrayList<DataScore>();
		String currentUser = "";
		String previousUser = "";
		Integer sumTotalScore = 0;
		Integer sumViews = 0;
		Integer counter = 0;
			    
	    for(DataScore ds : dsList){
	    	currentUser = ds.getUserName();
	    	if(!currentUser.equals(previousUser)){
	    		if(counter != 0){
	    			//Creates and Sets the new data score values
		    		DataScore newDS = new DataScore();
		    		newDS.setUserName(previousUser);
		    		newDS.setTotalScore(sumTotalScore);
		    		newDS.setViews(sumViews);
		    		result.add(newDS);
		    		
		    		//Reset Accumulators
		    		sumTotalScore = 0;
		    		sumViews = 0;	
	    		}
	    	}
	    	
	    	sumTotalScore = sumTotalScore + ds.getTotalScore();
    		sumViews = sumViews + ds.getViews();
    		counter++;
	    	previousUser = currentUser;
	    }
	    
	    Collections.sort(result, DataScore.TotalScoreComparitor);
	    return result;
	}
}

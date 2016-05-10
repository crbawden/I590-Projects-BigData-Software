package com.iu.info590.dnoell.utils;

import java.util.List;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.iu.info590.dnoell.db.DatabaseCRUD;
import com.iu.info590.dnoell.pagedata.PageData;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class OutputResults extends Thread {
	private static Logger logger = Logger.getLogger(OutputResults.class);
	
	public void outputToFile(List<PageData> pdList, String path, String outputFileName, 
			long totalResults) throws IOException{
		//Create the output file
		Utility.createFile(path, outputFileName);
		
		//Add header record to the file
		Utility.appendStringToFile(pageStatsHeader(), path + "/" + outputFileName);
		
		logger.debug("Exporting records to data file.");
		
		for(int i = 0; i < totalResults; i++){
			Utility.appendStringToFile(createPageStatsRecord(pdList.get(i)), path + "/" + outputFileName);	    
		}
		logger.debug("File created: " +  path + "/" + outputFileName);
	}
	
	public void outputToMongoDb(List<PageData> pdList, long totalResults) throws UnknownHostException, InterruptedException{		
		try {
			logger.debug("MongoDB: Preparing to Insert in database");
			
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient(Constants.local_host, Constants.remote_port); 			
			MongoDatabase mongoDB = mongoClient.getDatabase(Constants.databaseName);
		    MongoCollection<Document> collection = mongoDB.getCollection(Constants.collectionName);
		    if(collection != null){ 
			    logger.debug("MongoDB: Retrieved Collection");
			    int counter = 0;
			    for(int i = 0; i < totalResults; i++){
			    	DatabaseCRUD crud = new DatabaseCRUD();
			    	counter = crud.insertPageData(pdList.get(i), collection, counter);	
			    }		    
			    logger.info("    " + counter + " records inserted in database.");
		    }		
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	public void outputToSSHMongoDb(List<PageData> pdList, long totalResults) throws UnknownHostException, InterruptedException{ 
		Session session = null;
		
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
			//session.setPassword(SSH_PASSWORD);
			session.connect();
			session.setPortForwardingL(Constants.local_port, Constants.remote_host, Constants.remote_port); 
  
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient(Constants.local_host, Constants.local_port); 

			MongoDatabase mongoDB = mongoClient.getDatabase(Constants.databaseName);
		    MongoCollection<Document> collection = mongoDB.getCollection(Constants.collectionName);
		    if(collection != null){ 
			    logger.debug("MongoDB: Retrieved Collection");
			    int counter = 0;
			    for(int i = 0; i < totalResults; i++){
			    	DatabaseCRUD crud = new DatabaseCRUD();
			    	counter = crud.insertPageData(pdList.get(i), collection, counter);	
			    }		    
			    logger.info("    " + counter + " records inserted in database.");
		    }			     
		}
		catch(Exception e){
			System.out.println(e);
		}
		finally{
			try {
				session.delPortForwardingL(Constants.local_port);
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			session.disconnect(); 
		}
	}
	
	
	private String pageStatsHeader(){   
		String result = "SearchString | Title | Url | Description | SerialNumber | PubDate | Views | UserName | Subscribers";        
        return result;
    }
	
	private String createPageStatsRecord(PageData pgData){   
		String result = pgData.getSearchString() + "|" + pgData.getTitle() + "|" + pgData.getUrl() + "|" + pgData.getDescription() + "|" + pgData.getSerialNumber() + "|" + pgData.getPubDate() + "|" + pgData.getViews() + "|" + pgData.getUserName() + "|" + pgData.getSubscribers();        
        return result;
    }
}

package com.iu.info590.dnoell.db;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.iu.info590.dnoell.pagedata.PageData;
public class DatabaseCRUD {
	private Logger logger = Logger.getLogger(DatabaseCRUD.class);
	
	public int insertPageData(PageData pd, MongoCollection<Document> collection, int counter){
		if(!urlExists(pd.getUrl(), collection)){
    		Document document = new Document();
    		document.put("SearchString", pd.getSearchString());
    		document.put("Title", pd.getTitle());
    		document.put("Url", pd.getUrl());
    		document.put("Description", pd.getDescription());
    		document.put("SerialNumber", pd.getSerialNumber());
    		document.put("PubDate",pd.getPubDate()); 
    		document.put("Views", pd.getViews()); 
    		document.put("UserName", pd.getUserName()); 
    		document.put("Subscribers", pd.getSubscribers());
    		collection.insertOne(document);
   		
    		counter = counter + 1;
    		logger.debug("   " + counter + ". Record inserted for " + pd.getUrl());
    	}	
		return counter;
	}
	
	private boolean urlExists(String url, MongoCollection<Document> collection){
		Boolean result = false;
		BasicDBObject whereQuery = new BasicDBObject();
	    whereQuery.put("Url", url);
		
		Document myDoc = collection.find(whereQuery).first();
		if(myDoc != null)
			result = true;

		return result;
	}
}

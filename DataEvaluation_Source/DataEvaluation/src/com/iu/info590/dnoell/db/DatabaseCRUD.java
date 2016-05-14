package com.iu.info590.dnoell.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.iu.info590.dnoell.entities.PageData;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DatabaseCRUD {
	private Logger logger = Logger.getLogger(DatabaseCRUD.class);
	
	public List<PageData> retrieveSearchData(MongoCollection<Document> collection){
		List<PageData> result = new ArrayList<PageData>();
        
		FindIterable<Document> searchDataDocs = collection.find();
	    for(Document doc : searchDataDocs) {
            PageData pd = new PageData();
            pd.setDescription((String) doc.get("Description") );
            pd.setPubDate((String) doc.get("PubDate") ); 
            pd.setSearchString((String) doc.get("SearchString"));
            pd.setSerialNumber((String) doc.get("SerialNumber"));
            pd.setSubscribers(Integer.parseInt(doc.get("Subscribers").toString()));
            pd.setTitle((String) doc.get("Title"));
            pd.setUrl((String) doc.get("Url")); 
            pd.setUserName((String) doc.get("UserName"));
            pd.setViews(Integer.parseInt(doc.get("Views").toString()));
            result.add(pd);
        }    
	    logger.info("    " + result.size() + " records retrieved from database.");	
	    return result;
		
	}
}

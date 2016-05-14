package com.iu.info590.dnoell.utils;

import java.util.*;
import java.io.*;

//Source: http://www.javenue.info/post/40
public class Configuration {
    private static Configuration _instance = null;

    private Properties props = null;

    private Configuration() {
         props = new Properties();
    	try {
	    FileInputStream fis = new FileInputStream(new File(Constants.filesDir + "/" + Constants.propertiesFile));
	    props.load(fis);
    	}
    	catch (Exception e) {
    	    // catch Configuration Exception right here
    	}
    }

    public synchronized static Configuration getInstance() {
        if (_instance == null)
            _instance = new Configuration();
        return _instance;
    }

    // get property value by name
    public String getProperty(String key) {
        String value = null;
        if (props.containsKey(key))
            value = (String) props.get(key);
        else {
            // the property is absent
        }
        return value;
    }
}

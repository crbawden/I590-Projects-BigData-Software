package com.iu.info590.dnoell.utils;

import com.iu.info590.dnoell.entities.DataScore;
import com.iu.info590.dnoell.entities.PageData;

public class WordCount {
	
	public static DataScore getUrlScores(PageData pd){
		DataScore result = new DataScore();
		Integer searchWordScore = 0;
		Integer totalScore = 0;
		String[] searchWords = null;
		String searchWordOne = Constants.SearchWordOne;
		String searchWordTwo = Constants.SearchWordTwo;
		String searchWordThree = Constants.SearchWordThree;
		String searchWordFour = Constants.SearchWordFour;
		String searchWordFive = Constants.SearchWordFive;
		
		//Set constant values for data row
		result.setTitle(pd.getTitle());
		result.setUrl(pd.getUrl());
		result.setUserName(pd.getUserName());
		result.setViews(pd.getViews());
		result.setSubscribers(pd.getSubscribers());
		result.setDescription(pd.getDescription());
		
		//Set Record Counts
		searchWordScore = 0;
		searchWords = searchWordOne.split("|");
		for(int i = 0; i < searchWords.length; i++){
			String desc = pd.getDescription().toLowerCase();
			searchWordScore = searchWordScore + wordCountInString(desc, searchWords[i]);
		}
		totalScore = totalScore + searchWordScore;
		result.setSearchWordOneScore(searchWordScore);
		
		searchWordScore = 0;
		searchWords = searchWordTwo.split("|");
		for(int i = 0; i < searchWords.length; i++){
			String desc = pd.getDescription().toLowerCase();
			searchWordScore = searchWordScore + wordCountInString(desc, searchWords[i]);
		}
		totalScore = totalScore + searchWordScore;
		result.setSearchWordTwoScore(searchWordScore);
		
		searchWordScore = 0;
		searchWords = searchWordThree.split("|");
		for(int i = 0; i < searchWords.length; i++){
			String desc = pd.getDescription().toLowerCase();
			searchWordScore = searchWordScore + wordCountInString(desc, searchWords[i]);
		}
		totalScore = totalScore + searchWordScore;
		result.setSearchWordThreeScore(searchWordScore);
		
		searchWordScore = 0;
		searchWords = searchWordFour.split("|");
		for(int i = 0; i < searchWords.length; i++){
			String desc = pd.getDescription().toLowerCase();
			searchWordScore = searchWordScore + wordCountInString(desc, searchWords[i]);
		}
		totalScore = totalScore + searchWordScore;
		result.setSearchWordFourScore(searchWordScore);
		
		searchWordScore = 0;
		searchWords = searchWordFive.split("|");
		for(int i = 0; i < searchWords.length; i++){
			String desc = pd.getDescription().toLowerCase();
			searchWordScore = searchWordScore + wordCountInString(desc, searchWords[i]);
		}
		totalScore = totalScore + searchWordScore;
		result.setSearchWordFiveScore(searchWordScore);
		
		//Sets the Total score used for ordering
		result.setTotalScore(totalScore);
		
		return result;
	}
	
	private static Integer wordCountInString(String str, String word){
		Integer result = 0;
		
		int i;
	    int last = 0;
	    do {
	        i = str.indexOf(word, last);
	        if (i != -1) 
	        	result++;
	        
	        last = i+word.length();
	    } while(i != -1);
		
		return result;
	}

}

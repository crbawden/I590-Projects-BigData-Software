package com.iu.info590.dnoell.entities;

import java.util.Comparator;

public class DataScore implements Comparable<Object>{
	
	private String title = "";
	private String userName = "";
	private String description = "";
	private String url = "";
	private Integer views = 0;
	private Integer subscribers = 0;
	private Integer searchWordOneScore = 0;
	private Integer searchWordTwoScore = 0;
	private Integer searchWordThreeScore = 0;
	private Integer searchWordFourScore = 0;
	private Integer searchWordFiveScore = 0;
	private Integer totalScore = 0;
	
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
	public void setSearchWordOneScore(Integer val){
		searchWordOneScore = val;
	}
	public Integer getSearchWordOneScore(){
		return searchWordOneScore;
	}
	public void setSearchWordTwoScore(Integer val){
		searchWordTwoScore = val;
	}
	public Integer getSearchWordTwoScore(){
		return searchWordTwoScore;
	}
	public void setSearchWordThreeScore(Integer val){
		searchWordThreeScore = val;
	}
	public Integer getSearchWordThreeScore(){
		return searchWordThreeScore;
	}
	public void setSearchWordFourScore(Integer val){
		searchWordFourScore = val;
	}
	public Integer getSearchWordFourScore(){
		return searchWordFourScore;
	}
	public void setSearchWordFiveScore(Integer val){
		searchWordFiveScore = val;
	}
	public Integer getSearchWordFiveScore(){
		return searchWordFiveScore;
	}
	public void setTotalScore(Integer val){
		totalScore = val;
	}
	public Integer getTotalScore(){
		return totalScore;
	}
	
	@Override
	public int compareTo(Object comparestu) {
		int compareage=((DataScore)comparestu).getTotalScore();
		return compareage-this.totalScore;
	}
	
	 /*
	  * Based on http://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
	  * Comparator for sorting the list by TotalScore
	  */
    public static Comparator<DataScore> TotalScoreComparitor = new Comparator<DataScore>() {

	public int compare(DataScore ds1, DataScore ds2) {
	   Integer totalDataScore1 = ds1.getTotalScore();
	   Integer totalDataScore2 = ds2.getTotalScore();

	   //ascending order
	   //return totalDataScore.compareTo(totalDataScore2);

	   //descending order
	   return totalDataScore2.compareTo(totalDataScore1);
    }};
    
    public static Comparator<DataScore> UserNameComparitor = new Comparator<DataScore>() {

    	public int compare(DataScore ds1, DataScore ds2) {
    	   String userName1 = ds1.getUserName();
    	   String userName2 = ds2.getUserName();

    	   //ascending order
    	   return userName1.compareTo(userName2);

    	   //descending order
    	   //return userName2.compareTo(userName1);
        }};

    /*Comparator for sorting the list by roll no*/
    public static Comparator<DataScore> ViewsNumber = new Comparator<DataScore>() {

	public int compare(DataScore ds1, DataScore ds2) {

	   int views1 = ds1.getViews();
	   int views2 = ds2.getViews();

	   /*For ascending order*/
	   //return views1-views2;

	   /*For descending order*/
	   return views2-views1;
   }};
}

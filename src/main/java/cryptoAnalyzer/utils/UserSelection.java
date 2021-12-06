package cryptoAnalyzer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The UserSelection class stores all the selections made by the user and provides getter and setter
 * methods for each of the data points it stores.
 * 
 * @author	Matthew Cheverie
 * @author	Jack Di Falco
 * @author	Cole Duffy
 * @author	Namra Patel
 * @version	1.0
 * @since	1.0
 */
public class UserSelection {
	private String coin;
	private String interval;
	private Date startingdate;
	private String analysisType;
	private Date[] daysNeeded;
	
	/**
	 * A constructor method that initializes the respective user selection variables with the
	 * params that are passed in.
	 * 
	 * @param theCoin The coin whose data the user is interested in.
	 * @param theInterval The interval at which the user wants the data to be shown for.
	 * @param theMetric The metric of data the user wants to see.
	 * @param start The state date from which the data will be taken.
	 */
	public UserSelection(String theCoin, String theInterval,String theMetric, Date start) {
		coin = theCoin.replaceAll("\\s", "");
		interval = theInterval;
		startingdate = start;
		analysisType = theMetric;
	}
	
	/**
	 * Getter method that returns the coin name.
	 * @return String that represents the coin name.
	 */
	public String getCoin() {
		return this.coin;
	}
	
	/**
	 * Getter method that returns the interval that has been stored.
	 * @return Interval that is stored in UserSelection in String format.
	 */
	public String getInterval() {
		return this.interval;
	}
	
	/**
	 * Getter method that returns the start date that has been stored.
	 * @return Start date that is stored in UserSelection as a Date object. 
	 */
	public Date getDate() {
		return this.startingdate;
	}
	
	/**
	 * Getter method that returns the analysis type that has been stored.
	 * @return Analysis type that is stored in UserSelection as a String.
	 */
	public String getAnalysisType() {
		return this.analysisType;
	}
	
	/**
	 * Getter method that returns the array of days that are needed.
	 * @return An array of type Date containing the days needed.
	 */
	public Date[] getDaysNeeded() {
		return this.daysNeeded;
	}
	
	/**
	 * Sets the coin of interest for storage in UserSelection.
	 * @param theCoin String representing the coin name.
	 */
	public void setCoin(String theCoin) {
		this.coin = theCoin;
	}
	
	/**
	 * Sets the interval over which the data will be represented for storage in UserSelection.
	 * @param theInterval A string representing the interval of interest.
	 */
	public void setInterval(String theInterval) {
		this.interval = theInterval;
	}
	
	/**
	 * Sets the starting date for storage in UserSelection.
	 * @param theDate A Date object representing the start date from which the data will be collected.
	 */
	public void setDate(Date theDate) {
		this.startingdate = theDate;
	}
	
	/**
	 * Sets the analysis type that the user is interested in.
	 * @param type A string representing the analysis type.
	 */
	public void setAnalysisType(String type) {
		this.analysisType = type;
	}
}

package cryptoAnalyzer.utils;

import java.util.Date;
public class UserSelection {
	private String coin;
	private String interval;
	private Date date;
	private String analysisType;
	private Date[] daysNeeded;
	
	
	
	public String getCoin() {
		return this.coin;
	}
	public String getInterval() {
		return this.interval;
	}
	public Date getDate() {
		return this.date;
	}
	public String getAnalysisType() {
		return this.analysisType;
	}
	public void setCoin(String theCoin) {
		this.coin = theCoin;
	}
	public void setInterval(String theInterval) {
		this.interval = theInterval;
	}
	public void setDate(Date theDate) {
		this.date = theDate;
	}
	
	public void setAnalysisType(String type) {
		this.analysisType = type;
	}
	
	public Date[] getDaysNeeded() {
		return this.daysNeeded;
	}
	/*We would need a function to assign days needed based on the starting day and the interval.
	 * 
	 */
	
}

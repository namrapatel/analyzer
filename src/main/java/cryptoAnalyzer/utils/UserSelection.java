package cryptoAnalyzer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
public class UserSelection {
	private String coin;
	private String interval;
	private Date startingdate;
	private String analysisType;
	private Date[] daysNeeded;
	
	public UserSelection(String theCoin, String theInterval,String theMetric, Date start) {
		coin = theCoin.replaceAll("\\s", "");
		interval = theInterval;
		startingdate = start;
		analysisType = theMetric;
	}
	
	
	public String getCoin() {
		return this.coin;
	}
	public String getInterval() {
		return this.interval;
	}
	public Date getDate() {
		return this.startingdate;
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
		this.startingdate = theDate;
	}
	
	public void setAnalysisType(String type) {
		this.analysisType = type;
	}
	
	public Date[] getDaysNeeded() {
		return this.daysNeeded;
	}
}

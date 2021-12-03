package cryptoAnalyzer.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UserSelection {
	private String coin;
	private String interval;
	private String date;
	private String analysisType;
	
	
	public String getCoin() {
		return this.coin;
	}
	public String getInterval() {
		return this.interval;
	}
	public String getDate() {
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
	public void setDate(String theDate) {
		this.date = theDate;
	}
	
	public void setAnalysisType(String type) {
		this.analysisType = type;
	}
	
	
}

package cryptoAnalyzer.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class MarketCapAnalysis implements Analysis{
private UserSelection theSelection;

	public MarketCapAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	
	public Result perform() {
		Date startDate = theSelection.getDate();
		Date currentDate = new Date();
		String interval = theSelection.getInterval();
		String coin = theSelection.getCoin();
		ArrayList<Object> marketCap = new ArrayList<Object>();
		Object[] result = null;
		int count;
		marketCap.add(coin);
		DataFetcher apiCall = new DataFetcher();
		Date workingDate = startDate;
		while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {
			marketCap.add(apiCall.getMarketCapForCoin(coin.toLowerCase(), dateFormatter(workingDate)));
			Calendar c = Calendar.getInstance();
			c.setTime(workingDate);
			c.add(Calendar.DATE, 1);
			workingDate = c.getTime();
		}
		if(interval == "Daily") {													//Check spelling that is sent			
			//do analysis type on all spaces in  the ArrayList and store in result as an array
			result = marketCap.toArray();											
		}
		else if(interval == "Weekly") {
			count = 0;
			int size = marketCap.size() -1;
			result = new Object[(int) (Math.ceil((double)size/7))];
			double intervalResult = 0;
			int place = 0;
			if(size >= 7) {
				
				for(int i = 1; i < marketCap.size() -1;i++) {
					intervalResult += (double)marketCap.get(i);
					count ++;
					if(count == 7) {
						result[place] = intervalResult/7;
						place++;
						count = 0;
						size -=7;
						intervalResult = 0;
					}
					if(place == result.length -1) {
						// add up the remaining interval if it is anything other than 7
						if(size < 7) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/size;
						}
						//if the last inverval is a perfect week
						else if (size == 7) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/7;
						}
					}
				}
				
			
			}
			
			else if (size < 7 && size > 0){
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) marketCap.get(count1);
					intervalResult = intervalResult + j;
					count1++;
				}
				result[place] = intervalResult / size;
			}
		}
		else if (interval == "Monthly") {
			count = 0;
			int size = marketCap.size() -1;
			result = new Object[(int) (Math.ceil((double)size/30))];
			double intervalResult = 0;
			int place = 0;
			if(size >= 30) {
				
				for(int i = 1; i < marketCap.size() -1;i++) {
					intervalResult += (double)marketCap.get(i);
					count ++;
					if(count == 30) {
						result[place] = intervalResult/30;
						place++;
						count = 0;
						size -=30;
						intervalResult = 0;
					}
					if(place == result.length -1) {
						// add up the remaining interval if it is anything other than 7
						if(size < 30) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/size;
						}
						//if the last inverval is a perfect week
						else if (size == 30) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/30;
						}
					}
				}
				
			
			}
			
			else if (size < 30 && size > 0){
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) marketCap.get(count1);
					intervalResult = intervalResult + j;
					count1++;
				}
				result[place] = intervalResult / size;
			}
			
			
		}
		else if (interval == "Yearly") {
			count = 0;
			int size = marketCap.size() -1;
			result = new Object[(int) (Math.ceil((double)size/7))];
			double intervalResult = 0;
			int place = 0;
			if(size >= 365) {
				
				for(int i = 1; i < marketCap.size() -1;i++) {
					intervalResult += (double)marketCap.get(i);
					count ++;
					if(count == 365) {
						result[place] = intervalResult/365;
						place++;
						count = 0;
						size -=365;
						intervalResult = 0;
					}
					if(place == result.length -1) {
						// add up the remaining interval if it is anything other than 7
						if(size < 365) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/size;
						}
						//if the last inverval is a perfect week
						else if (size == 365) {
							for(int j = i; j < marketCap.size() -1; j++) {
								intervalResult +=(double)marketCap.get(j);
							}
							result[place] = intervalResult/365;
						}
					}
				}
				
			
			}
			
			else if (size < 365 && size > 0){
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) marketCap.get(count1);
					intervalResult = intervalResult + j;
					count1++;
				}
				result[place] = intervalResult / size;
			}
			
		}
		Result finalProduct = new Result(result);
		return finalProduct;
		//use theSelection to generate HTTP get request for data.
		//Parse JSON file to generate Object[] columnNames and Object[][]data
		//ResultData theData = new ResultData(columnNames,data);
		//Result theResult = new Result(theData);
		//return theResult;
	}
	private String dateFormatter(Date theDate) {
		String datePatern = "dd-MM-yyyy";
	    SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
	    return dateFormatter.format(theDate);
	}
}

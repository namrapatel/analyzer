/**
 * The MarketCapAnalysis class takes in a UserSelection object and using that, calculates the price for the 
 * selected coin and interval
 * @author Matthew Cheverie
 * @author Cole Duffy
 * @author Jack Di Falco
 * @author Namra Patel
 * @version 1.0
 * @since 1.0
 */

package cryptoAnalyzer.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.math.*;

public class PriceAnalysis implements Analysis {
	
	private UserSelection theSelection;
	
	public PriceAnalysis(UserSelection selection) {									//Constructor
		theSelection = selection;
	}
	
	public Result perform() {														//Begin perform function
		Date startDate = theSelection.getDate();									//Initialize needed variables
		Date currentDate = new Date();
		String interval = theSelection.getInterval();
		String coin = theSelection.getCoin();
		ArrayList<Object> price = new ArrayList<Object>();							//Initialize ArrayList to store data from JSON file
		Object[] result = null;	
		int count;
		price.add(coin);
		DataFetcher apiCall = new DataFetcher();
		Date workingDate = startDate;
		while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {					//While the date is before or equal to the current date
			price.add(apiCall.getPriceForCoin(coin.toLowerCase(), dateFormatter(workingDate)));		//Store the price value for that coin that day
			Calendar c = Calendar.getInstance();
			c.setTime(workingDate);
			c.add(Calendar.DATE, 1);												//Update the day to the next
			workingDate = c.getTime();
			
			
		}
		if(interval == "Daily") {													//If requested interval is Daily		
			result = price.toArray();												//Store ArrayList in the result array
		}
		else if(interval == "Weekly") {												//If requested interval is Weekly
			count = 0;
			int size = price.size() -1;
			result = new Object[(int) (Math.ceil((double)size/7))];					//Set result array's size to total days / 7, rounded up
			double intervalResult = 0;
			int place = 0;
			if(size >= 7) {
				for(int i = 1; i < price.size() -1;i++) {							//For each cell in the price ArrayList
					intervalResult += (double)price.get(i);							//Increase the intervalResult counter
					count ++;
					if(count == 7) {												//Once the count variable reaches 7
						result[place] = intervalResult/7;							//Divide intervalResult by 7 and store in result array
						place++;													//Update placeholder variables for next week average
						count = 0;
						size -=7;
						intervalResult = 0;
					}
					if(place == result.length -1) {
						if(size < 7) {												//if the interval is less than a week
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);				//add up the remaining days data
							}
							result[place] = intervalResult/size;					//and divide by number of days, store in result 
						}
						else if (size == 7) {										//if the last interval is a perfect week
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);				//add up the remaining days
							}
							result[place] = intervalResult/7;						//and divide by 7, store in result
						}	
					}
				}
				
			
			}
			
			else if (size < 7 && size > 0){											//If the interval is less than a week
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) price.get(count1);
					intervalResult = intervalResult + j;							//add up the remaining days
					count1++;
				}
				result[place] = intervalResult / size;								//and divide by number of days, store in result
			}	
			
		}
		else if (interval == "Monthly") {											//If requested interval is Monthly
			count = 0;																//Do the same as weekly but using 30 instead of 7
			int size = price.size() -1;
			result = new Object[(int) (Math.ceil((double)size/30))];
			double intervalResult = 0;
			int place = 0;
			if(size >= 30) {
				
				for(int i = 1; i < price.size() -1;i++) {
					intervalResult += (double)price.get(i);
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
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);
							}
							result[place] = intervalResult/size;
						}
						//if the last inverval is a perfect week
						else if (size == 30) {
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);
							}
							result[place] = intervalResult/30;
						}
					}
				}
				
			
			}
			
			else if (size < 30 && size > 0){
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) price.get(count1);
					intervalResult = intervalResult + j;
					count1++;
				}
				result[place] = intervalResult / size;
			}
			
			
		}
		else if (interval == "Yearly") {											//If requested interval is yearly
			count = 0;																//Same as weekly but using 365 instead of 7
			int size = price.size() -1;
			result = new Object[(int) (Math.ceil((double)size/7))];
			double intervalResult = 0;
			int place = 0;
			if(size >= 365) {
				
				for(int i = 1; i < price.size() -1;i++) {
					intervalResult += (double)price.get(i);
					count ++;
					if(count == 365) {
						result[place] = intervalResult/365;
						place++;
						count = 0;
						size -=365;
						intervalResult = 0;
					}
					if(place == result.length -1) {
						if(size < 365) {
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);
							}
							result[place] = intervalResult/size;
						}
						else if (size == 365) {
							for(int j = i; j < price.size() -1; j++) {
								intervalResult +=(double)price.get(j);
							}
							result[place] = intervalResult/365;
						}
					}
				}
				
			
			}
			
			else if (size < 365 && size > 0){
				int count1 = 1;
				for(int i = size; i>0; i--) {
					double j = (double) price.get(count1);
					intervalResult = intervalResult + j;
					count1++;
				}
				result[place] = intervalResult / size;
			}
			
		}
		Result finalProduct = new Result(result);
		return finalProduct;

	}
	private String dateFormatter(Date theDate) {
		String datePatern = "dd-MM-yyyy";
	    SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
	    return dateFormatter.format(theDate);
	}
}
package cryptoAnalyzer.utils;

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
		//ArrayList<Object> price = new ArrayList<Object>();
		ArrayList<Object> marketCap = new ArrayList<Object>();
		//ArrayList<Object> volume = new ArrayList<Object>();
		Object[] result = null;
		int count = 1;
		//price.set(0, coin);
		marketCap.set(0, coin);
		//volume.set(0, coin);
		DataFetcher apiCall = new DataFetcher();
		Date workingDate = startDate;
		while (startDate.before(currentDate)||startDate.equals(currentDate)) {
			String thisDay = workingDate.toString();								//may be the error
			//price.set(count, apiCall.getPriceForCoin(coin, thisDay));
			marketCap.set(count, apiCall.getMarketCapForCoin(coin, thisDay));
			//volume.set(count, apiCall.getVolumeForCoin(coin, thisDay));
			count++;
		}
		if(interval == "Daily") {													//Check spelling that is sent			
			//do analysis type on all spaces in  the ArrayList and store in result as an array
			result = marketCap.toArray();											
		}
		else if(interval == "Weekly") {
			workingDate = startDate;
			count = 1;
			int size = marketCap.size();
			int intervalResult = 0, place = 1;
			while(size >= 7) {
				for(int i=0; i<7; i++) {
					int j = (Integer) marketCap.get(count);
					intervalResult = intervalResult + j;							//Analysis type
					count++;
				}
				result[place] = intervalResult / 7;
				place++;
				intervalResult = 0;
				size -= 7;
			}
			if (size < 7 && size > 0){												//Has to be > 0 if in above while loop size = 7
				for(int i = size; i>0; i--) {
					int j = (Integer) marketCap.get(count);
					intervalResult = intervalResult + j;
					count++;
				}
				result[place] = intervalResult / size;
			}
		}
		else if (interval == "Monthly") {
			
			
			
		}
		else if (interval == "Yearly") {
			workingDate = startDate;
			count = 1;
			int size = marketCap.size();
			int intervalResult = 0, place = 1;
			while(size >= 365) {
				for(int i=0; i<365; i++) {
					int j = (Integer) marketCap.get(count);
					intervalResult = intervalResult + j;			//Analysis type
					count++;
				}
				result[place] = intervalResult / 7;
				place++;
				intervalResult = 0;
				size -= 365;
			}
			if (size < 365 && size > 0){
				for(int i = size; i>0; i--) {
					int j = (Integer) marketCap.get(count);
					intervalResult = intervalResult + j;
					count++;
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
}

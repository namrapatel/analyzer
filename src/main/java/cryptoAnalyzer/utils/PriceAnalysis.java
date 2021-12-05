package cryptoAnalyzer.utils;

import java.util.ArrayList;
import java.util.Date;

public class PriceAnalysis implements Analysis {
	
	private UserSelection theSelection;
	
	public PriceAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	public Result perform() {
		Date startDate = theSelection.getDate();
		Date currentDate = new Date();
		String interval = theSelection.getInterval();
		String coin = theSelection.getCoin();
		ArrayList<Object> price = new ArrayList<Object>();
		Object[] result = null;
		int count = 1;
		price.set(0, coin);
		DataFetcher apiCall = new DataFetcher();
		Date workingDate = startDate;
		while (startDate.before(currentDate)||startDate.equals(currentDate)) {
			String thisDay = workingDate.toString();								//may be the error
			price.set(count, apiCall.getPriceForCoin(coin, thisDay));
			count++;
		}
		if(interval == "Daily") {													//Check spelling that is sent			
			//do analysis type on all spaces in  the ArrayList and store in result as an array
			result = price.toArray();											
		}
		else if(interval == "Weekly") {
			workingDate = startDate;
			count = 1;
			int size = price.size();
			int intervalResult = 0, place = 1;
			while(size >= 7) {
				for(int i=0; i<7; i++) {
					int j = (Integer) price.get(count);
					intervalResult = intervalResult + j;							//Analysis type
					count++;
				}
				result[place] = intervalResult / 7;
				place++;
				intervalResult = 0;
				size -= 7;
			}
			if (size < 7 && size > 0){
				for(int i = size; i>0; i--) {
					int j = (Integer) price.get(count);
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
			int size = price.size();
			int intervalResult = 0, place = 1;
			while(size >= 365) {
				for(int i=0; i<365; i++) {
					int j = (Integer) price.get(count);
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
					int j = (Integer) price.get(count);
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

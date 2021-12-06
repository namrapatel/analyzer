package cryptoAnalyzer.utils;

public class PercentPriceAnalysis implements Analysis{
private UserSelection theSelection;
	
	public PercentPriceAnalysis(UserSelection selection) {				//Constructor
		theSelection = selection;
	}
	
	public Result perform() {
		PriceAnalysis data = new PriceAnalysis(theSelection);				
		Result result = data.perform();									//Do the analysis for Price
		Object[] workingArray = result.getResultData();					//Initialize working array with data from the analysis
		Object[] resultArray = new Object[workingArray.length-1];		//Create array for the result data
		int size = workingArray.length -1;
		resultArray[0]=theSelection.getCoin();							//Set first cell as coin name
		int count = 1;
		while (count < size - 1) {										//For each cell in the array
			double first = (double ) workingArray[count];
			double second = (double )workingArray[count+1];
			double change = second - first;								//Calculate the percent change from one cell to the next
			double answer = (change / first) * 100;
			resultArray[count] = answer;
			count++;
		}
		Result finalData = new Result(resultArray);						//Create result object to store the data
		return finalData;												//Return the result
		
	}
}

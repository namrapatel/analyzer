package cryptoAnalyzer.utils;

public class PercentMarketCapAnalysis implements Analysis {
private UserSelection theSelection;
	
	public PercentMarketCapAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	public Result perform() {
		MarketCapAnalysis data = new MarketCapAnalysis(theSelection);				
		Result result = data.perform();
		Object[] workingArray = result.getResultData();
		Object[] resultArray = new Object[workingArray.length-1];
		int size = workingArray.length -1;
		resultArray[0]=theSelection.getCoin();
		int count = 1;
		while (count < resultArray.length) {
			double first = (double ) workingArray[count];
			double second = (double )workingArray[count+1];
			double change = second - first;
			double answer = (change / first) * 100;
			resultArray[count] = answer;
			count++;
		}
		Result finalData = new Result(resultArray);
		return finalData;
		
	}
}

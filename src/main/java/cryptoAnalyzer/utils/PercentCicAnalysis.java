package cryptoAnalyzer.utils;

import java.util.ArrayList;
import java.util.Date;

public class PercentCicAnalysis implements Analysis {
private UserSelection theSelection;
	
	public PercentCicAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	public Result perform() {
		CicAnalysis data = new CicAnalysis(theSelection);				//Do the analysis for MarketCap
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

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
		int size = workingArray.length;
		int count = 1;
		while (count < size - 1) {
			int first = (Integer) workingArray[count];
			int second = (Integer) workingArray[count+1];
			int change = second - first;
			int answer = (change / first) * 100;
			workingArray[count] = answer;
		}
		Result finalData = new Result(workingArray);
		return finalData;
		//use theSelection to generate HTTP get request for data.
		//Parse JSON file to generate Object[] columnNames and Object[][]data
		//ResultData theData = new ResultData(columnNames,data);
		//Result theResult = new Result(theData);
		//return theResult;
	}
}

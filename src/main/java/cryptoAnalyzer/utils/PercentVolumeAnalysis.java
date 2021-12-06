package cryptoAnalyzer.utils;

public class PercentVolumeAnalysis implements Analysis {
private UserSelection theSelection;
	
	public PercentVolumeAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	public Result perform() {
		VolumeAnalysis data = new VolumeAnalysis(theSelection);				
		Result result = data.perform();
		Object[] workingArray = result.getResultData();
		Object[] resultArray = new Object[workingArray.length-1];
		int size = workingArray.length -1;
		resultArray[0]=theSelection.getCoin();
		int count = 1;
		while (count < size - 1) {
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

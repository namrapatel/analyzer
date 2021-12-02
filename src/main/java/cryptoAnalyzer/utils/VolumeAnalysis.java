package cryptoAnalyzer.utils;

public class VolumeAnalysis implements Analysis {
private UserSelection theSelection;
	
	public VolumeAnalysis(UserSelection selection) {
		theSelection = selection;
	}
	
	public Result perform() {
		//use theSelection to generate HTTP get request for data.
		//Parse JSON file to generate Object[] columnNames and Object[][]data
		//ResultData theData = new ResultData(columnNames,data);
		//Result theResult = new Result(theData);
		//return theResult;
		return null; // Temp
	}
}

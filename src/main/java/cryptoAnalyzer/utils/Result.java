package cryptoAnalyzer.utils;

public class Result {
	private ResultData resultData; // This is where the data from analysis gets stored
	
	
	public Result(ResultData data) {
		resultData = data;
		
	}
	public ResultData getResultData() {
		return this.resultData;
	}
	
}

package cryptoAnalyzer.utils;

public class Result {
	private ResultData resultData; // This is where the data from analysis gets stored
	private DataVisualizationCreator observers;
	
	public Result(ResultData data) {
		resultData = data;
		observers = new DataVisualizationCreator(this);
	}
	public ResultData getResultData() {
		return this.resultData;
	}
	public void notifyObservers() {
		this.observers.createCharts();
	}
}

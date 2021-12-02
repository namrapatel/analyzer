package cryptoAnalyzer.utils;

public class ResultData {
	private Object[] columnNames;
	private Object [] data;
	
	public ResultData(Object[] colNames, Object [] theData) {
		columnNames = colNames;
		data = theData;
	}
	public Object[] getColNames() {
		return this.columnNames;
	}
	public Object[] getData(){
		return this.data;
	}
}

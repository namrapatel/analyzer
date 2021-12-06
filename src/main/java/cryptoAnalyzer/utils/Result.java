/**
 * Class used to represent the analysis result
 * 
 * @author 	Matthew Cheverie
 * @author 	Jack Di Falco
 * @author 	Cole Duffy
 * @author 	Namra Patel
 * @version 1.0
 * @since 	1.0 
 * 
 */
package cryptoAnalyzer.utils;

public class Result {
	
	private Object[] resultData;	

	 /**
	  * Constructor
	  * @param Array of data objects used to represent result data
	  */
	public Result(Object[] data) {
		resultData = data;
	}
	/**
	 * Getter for resultData array
	 * @return array with the result data
	 */
	public Object[] getResultData() {
		return this.resultData;
	}
	
}

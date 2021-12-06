package cryptoAnalyzer.utils;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that implements methods to handle dates and data for the chart and also getter methods
 * to access dates, data, user selections, a row, and a column.
 * 
 * @author	Matthew Cheverie
 * @author	Jack Di Falco
 * @author	Cole Duffy
 * @author	Namra Patel
 * @version	1.0
 * @since	1.0
 */
public class ChartData {
	private Object[] dates;
	private Object[][] theData;
	private UserSelection theSelection;
	private int row;
	private int col;
	
	/**
	 * Constructor that takes a list of results and creates dates and data from it, and takes the user selection
	 * and sets it for to the local variable theSelection in ChartData. 
	 * @param listOfResults A List of type Result that contains all the results from the analysis. 
	 * @param selection A UserSelection object that contains the selections made by the user.
	 */
	public ChartData(List<Result> listOfResults, UserSelection selection) {
		theSelection = selection;
		dates = createdates(listOfResults.get(0).getResultData());
		theData = createData(listOfResults);
	}
	
	/**
	 * Getter method that gets the array of dates that are a part of the result. 
	 * @return An array of type Object that contains all the dates that are a part of the result.
	 */
	public Object[] getDates() {
		return this.dates;
	}
	
	/**
	 * Getter method that returns the data that is a part of the result.
	 * @return A 2D array of type Object that contains the data of the result.
	 */
	public Object[][] getData(){
		return this.theData;
	}
	
	/**
	 * Getter method that returns the user's selection as a UserSelection object.
	 * @return A UserSelection object containing all the selections that were made by the user.
	 */
	public UserSelection getUserSelection() {
		return this.theSelection;
	}
	
	/**
	 * Getter method that returns the row.
	 * @return The row as an int.
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Getter method that returns the column.
	 * @return The column as an int.
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Method that checks the user selection for the user's desired interval and creates an 
	 * array of dates based on the interval.
	 * @param data An array of type Object containing the data from the result.
	 * @return An array of type Object that contains the dates.
	 */
	private Object[] createdates(Object[] data) {
		Object[] Dates = new Object[data.length-1];
		Date currentDate = new Date();
		Date workingDate = theSelection.getDate();
		int count = 0;
		if(theSelection.getInterval() == "Daily") {
			while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {
				Dates[count] = dateFormatter(workingDate);
				Calendar c = Calendar.getInstance();
				c.setTime(workingDate);
				c.add(Calendar.DATE, 1);
				workingDate = c.getTime();
				count++;

			}
		}
		else if(theSelection.getInterval() == "Weekly") {
			while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {
				Dates[count] = dateFormatter(workingDate);
				Calendar c = Calendar.getInstance();
				c.setTime(workingDate);
				c.add(Calendar.DATE, 7);
				workingDate = c.getTime();
				count++;

			}
		}
		else if(theSelection.getInterval() == "Monthly") {
			while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {
				Dates[count] = dateFormatter(workingDate);
				Calendar c = Calendar.getInstance();
				c.setTime(workingDate);
				c.add(Calendar.DATE, 30);
				workingDate = c.getTime();
				count++;

			}
			
		}
		else if(theSelection.getInterval() == "Yearly") {
			while (workingDate.before(currentDate)||workingDate.equals(currentDate)) {
				Dates[count] = dateFormatter(workingDate);
				Calendar c = Calendar.getInstance();
				c.setTime(workingDate);
				c.add(Calendar.DATE, 365);
				workingDate = c.getTime();
				count++;

			}
			
		}
		return Dates;
	}
	
	/**
	 * Method that takes a List of type Result as a param and creates a 2D array of type
	 * Object that contains the data that is readable by the chart.
	 * @param theResults List of type Result containing the results of the analyses.
	 * @return A 2D array of type Object containing the data as readable by the charts.
	 */
	private Object[][] createData(List<Result> theResults){
		row = theResults.size();
		col = theResults.get(0).getResultData().length;
		Object[][] data = new Object[row][col]; 
		for(int i = 0; i <row;i++) {
			for(int j = 0; j < col; j++) {
				data[i][j] = theResults.get(i).getResultData()[j];
			}
		}
		return data;
		
	}
	
	/**
	 * Helper method that formats the date into a format that is accepted by the CoinGecko APi.
	 * @param theDate A date in Java Date type format.
	 * @return A String formatted as DD-MM-YYYY of the date that was passed in param.
	 */
	private String dateFormatter(Date theDate) {
		String datePatern = "dd-MM-yyyy";
	    SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
	    return dateFormatter.format(theDate);
	}
}
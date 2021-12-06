package cryptoAnalyzer.utils;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChartData {
	private Object[] dates;
	private Object[][] theData;
	private UserSelection theSelection;
	private int row;
	private int col;
	
	public ChartData(List<Result> listOfResults, UserSelection selection) {
		theSelection = selection;
		dates = createdates(listOfResults.get(0).getResultData());
		theData = createData(listOfResults);
	}
	public Object[] getDates() {
		return this.dates;
	}
	public Object[][] getData(){
		return this.theData;
	}
	public UserSelection getUserSelection() {
		return this.theSelection;
	}
	public int getRow() {
		return this.row;
	}
	public int getCol() {
		return this.col;
	}
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
	private String dateFormatter(Date theDate) {
		String datePatern = "dd-MM-yyyy";
	    SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
	    return dateFormatter.format(theDate);
	}
	
}

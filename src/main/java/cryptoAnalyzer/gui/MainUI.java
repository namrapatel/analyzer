package cryptoAnalyzer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import cryptoAnalyzer.utils.Analysis;
import cryptoAnalyzer.utils.AvailableCryptoList;
import cryptoAnalyzer.utils.DataVisualizationCreator;

import cryptoAnalyzer.utils.RestrictedCryptoList;



import cryptoAnalyzer.utils.Factory;
import cryptoAnalyzer.utils.UserSelection;
import cryptoAnalyzer.utils.Result;
import cryptoAnalyzer.utils.ChartData;


/**
 * CS 2212 
 * The MainUI class repesents the main window of this program.
 * Users can add to/remove from a list of cryptocurrencies to pull data from the CoinGeko API. 
 * The user needs to select a starting date for the search interval, a metric to search, and a time interval for which to display the data
 * The interval goes from the starting date selected to the current date that the system returns
 * @author Matthew Cheverie
 * @author Cole Duffy
 * @author Namra Patel
 * @author Jack DiFalco
 *
 */
public class MainUI extends JFrame implements ActionListener{
	
	/*Declare instance variables */
	
	private static final long serialVersionUID = 1L;
	
	//Declare instances of the differnt UI's we use
	private static MainUI instance;
	private static loginUi ins;
	
	//Declare the JPanel object that will hold our graphs
	private JPanel stats;
	
	// Declare the Java swing objects that users interface with
	//These need to be instance variables so we can access them all throughout this UI class
	private JComboBox<String> metricsList;
	private JComboBox<String> intervalList;
	private JDatePickerImpl datePicker;
	private JTextArea selectedCryptoList;
	private JComboBox<String> cryptoList;
	
	//Declare the list which we limit the users ability to search cryptos for
	private RestrictedCryptoList restrictedCoins;
	
	//Declare the lists needed to store all of the Data calculate analysis' and display them
	private List<Result> listOfResults; // need a list of results because we need to keep track of each analysis for each coin
	private List<UserSelection> listOfSelections;  // need a list because we need to store a new selection for each coin in the list
	
	//Declare variables that represent the user selections
	private List<String> selectedList;
	private String selectedMetric = "price"; //Defaulted to price if the user does not change the selection
	private Date selectedDate;
	private String selectedInterval = "Daily"; //Defaulted to Daily if the user does not change the selection
	
	//Declare the object that will store all result data that is needed to display charts
	private ChartData theChartData;
	
	/**
	 * This is the getter Class for the Instance variable called instance.
	 * If instance is null, create a new one
	 * @return MainUI instance - Returns the Instance variable called instance
	 */
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	@SuppressWarnings("serial")
	/**
	 * This is the constructor for the MainUI. It is made private to ensure that no other class can create a new 
	 * instance of MainUI
	 */
	private MainUI() {
		
		// Set window title
		super("Crypto Analysis Tool");

		// Set top bar
		JLabel chooseCountryLabel = new JLabel("Choose a cryptocurrency: ");
		String[] cryptoNames = AvailableCryptoList.getInstance().getAvailableCryptos();
		
		//Create list of Restricted coins 
		restrictedCoins = new RestrictedCryptoList(cryptoNames);
		List <String> theCoins = restrictedCoins.getRestrictedCoins();
		System.out.println("Restricted Coins:");
		for(int i = 0; i <theCoins.size();i ++) {
			System.out.println(theCoins.get(i));
		}
		
		cryptoList = new JComboBox<String>(cryptoNames);
		
		selectedList = new ArrayList<>();
		
		JButton addCrypto = new JButton("+");
		addCrypto.setActionCommand("add");
		addCrypto.addActionListener(this);
		
		JButton removeCrypto = new JButton("-");
		removeCrypto.setActionCommand("remove");
		removeCrypto.addActionListener(this);
		
		JPanel north = new JPanel();
		north.add(chooseCountryLabel);
		north.add(cryptoList);
		north.add(addCrypto);
		north.add(removeCrypto);
		

		// Set bottom bar
		JLabel from = new JLabel("From");
		UtilDateModel dateModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
		
		datePicker = new JDatePickerImpl(datePanel, new AbstractFormatter() {
			private String datePatern = "dd/MM/yyyy";
			
		    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePatern);
		    private String currentDate = java.time.LocalDate.now().toString();

		    @Override
		    public Object stringToValue(String text) throws ParseException {
		        return dateFormatter.parseObject(text);
		    }

		    @Override
		    public String valueToString(Object value) throws ParseException {
		        if (value != null) {
		            Calendar cal = (Calendar) value;
		            String dateReturn = dateFormatter.format(cal.getTime());
		            if(verifyDate(currentDate,dateReturn)) {
		            	selectedDate = dateFormatter.parse(dateReturn); //Selected Date variable gets assigned here
		            	return dateReturn;
		            }
		            else {
		            	JOptionPane.showMessageDialog(null,"Selected Date was in the future!. Please "
		            			+ "select a new date","Invalid Date",JOptionPane.INFORMATION_MESSAGE);
		            	return "";
		            }
		        }

		        return "";
		    }
		    
		    
		});
		
		
		
		
		JButton refresh = new JButton("Refresh");
		refresh.setActionCommand("refresh");
		refresh.addActionListener(this);

		JLabel metricsLabel = new JLabel("        Metrics: ");

		Vector<String> metricsNames = new Vector<String>();
		metricsNames.add("Price");
		metricsNames.add("MarketCap");
		metricsNames.add("Volume");
		metricsNames.add("Coins in circulation");
		metricsNames.add("Percent Change in Unit Price");
		metricsNames.add("Percent Change in MarketCap");
		metricsNames.add("Percent Change in Volume");
		metricsNames.add("Percent Change of Coins in Circulation");
	    metricsList = new JComboBox<String>(metricsNames);
		metricsList.setActionCommand("metric");
		metricsList.addActionListener(this);

		JLabel intervalLabel = new JLabel("        Choose interval: ");

		Vector<String> intervalNames = new Vector<String>();
		intervalNames.add("Daily");
		intervalNames.add("Weekly");
		intervalNames.add("Monthly");
		intervalNames.add("Yearly");

		intervalList = new JComboBox<String>(intervalNames);
		intervalList.setActionCommand("interval");
		intervalList.addActionListener(this);
		
		JPanel south = new JPanel();
		south.add(from);
		south.add(datePicker);
		
		south.add(metricsLabel);
		south.add(metricsList);

		south.add(intervalLabel);
		south.add(intervalList);
		south.add(refresh);

		
		JLabel selectedCryptoListLabel = new JLabel("List of selected cryptocurrencies: ");
		selectedCryptoList = new JTextArea(16, 10);
		JScrollPane selectedCryptoScrollPane = new JScrollPane(selectedCryptoList);
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		east.add(selectedCryptoListLabel);
		east.add(selectedCryptoScrollPane);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(1250,650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));
		
		west.add(stats);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
	}
	
	/**
	 * This function will update the stats Jpanel
	 * @param component - A java swing component of type JComponent
	 */
	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}

	/**
	 * This function will verify that the selected date is not a future date relative to the current date
	 * @param currdate - The current date in the form of a String yyyy/mm/dd
	 * @param selectedDate - The selected date in the form of a string dd/mm/yyyy
	 * @return True if the date was verified to be valid, False if the date is a future date.
	 */
	public Boolean verifyDate(String currdate, String selectedDate) {
		String[] currArray = currdate.split("-");
		String[] selArray = selectedDate.split("/");
		
		if(Integer.parseInt(selArray[2]) >Integer.parseInt(currArray[0])) 
			return false;
		if(Integer.parseInt(selArray[1]) >= Integer.parseInt(currArray[1]) && Integer.parseInt(selArray[0]) > Integer.parseInt(currArray[2])) {
				return false;
		}
		return true;
		
		//sel = d/m/y
		//cur = y/m/day
	}
	
	/**
	 * The Main execution point for the MainUI
	 * @param args - A String[] representing the command line arguments
	 */
	public static void main(String[] args) {
		
		ins = loginUi.getInstance();
		ins.Display();
		Boolean flag = ins.getHasSubmitted();
		while(flag == false && ins.getStatus() == false) {
			flag = ins.getHasSubmitted();
			System.out.print("");
		}
		
		if(ins.DestroyOnSucess() && ins.getStatus() == true) {
			JFrame frame = MainUI.getInstance();
			frame.setSize(900, 600);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	
	/**
	 * The method implemented from ActionListener that will be overridden
	 * This method will allow us to assign functionality to each of the Java.swing items such as buttons and selection lists
	 * @param e - an event of type ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		//If the refresh button is pressed
		if ("refresh".equals(command)) {
			
			if(selectedList.isEmpty()) {
				JOptionPane.showMessageDialog(null,"There are no cryptocurrencies selected. Please make at least one selection",
						"Empty Coin List",JOptionPane.INFORMATION_MESSAGE);
			}
			if(selectedDate == null) {
				JOptionPane.showMessageDialog(null,"There is no selected starting date. Please select a starting date!",
						"No starting Date",JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				listOfResults = new ArrayList<Result>();
				listOfSelections = new ArrayList<UserSelection>();
				for(int i = 0; i <selectedList.size();i++) {
					UserSelection newSelection = new UserSelection(selectedList.get(i),selectedInterval,selectedMetric,selectedDate);
					listOfSelections.add(newSelection);
				}
				
			
				
				
				for(int i =0; i < listOfSelections.size(); i ++) {
					Factory theFactory = new Factory();
					Analysis newAnalysis = theFactory.create(listOfSelections.get(i));
					Result newResult = newAnalysis.perform();
					listOfResults.add(newResult);
					
				}
				
				
				theChartData = new ChartData(listOfResults, listOfSelections.get(0));
				stats.removeAll(); // keep this line
				DataVisualizationCreator creator = new DataVisualizationCreator(theChartData); 
				creator.createCharts();
			}
			
			
			
		//If the add coin button pressed	
		} else if ("add".equals(command)) {
			if(selectedList.contains(cryptoList.getSelectedItem())) {
				JOptionPane.showMessageDialog(null,"The Coin you wish to add is already in the list!",
            			"Invalid Add",JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(!restrictedCoins.getRestrictedCoins().contains(cryptoList.getSelectedItem().toString())) {
					selectedList.add(cryptoList.getSelectedItem().toString());
					String text = "";
					for (String crypto: selectedList)
						text += crypto + "\n";
				selectedCryptoList.setText(text);
				}
				else {
					JOptionPane.showMessageDialog(null,"The cryptocurrency you have chosen belongs to the list "
							+ "which we have disabled data fetching for. Please select another one.",
	            			"Invalid Cryptocurrency",JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		//If the remove coin button is pressed
		} else if ("remove".equals(command)) {
			if(selectedList.contains(cryptoList.getSelectedItem())) {
				selectedList.remove(cryptoList.getSelectedItem());
				String text = "";
				for (String crypto: selectedList)
					text += crypto + "\n";
				
				selectedCryptoList.setText(text);
			}
			else {
				JOptionPane.showMessageDialog(null,"The Coin you wish to remove is not added to the list!",
            			"Invalid Remove",JOptionPane.INFORMATION_MESSAGE);
			}
			
		// If the user selects a metric	
		}else if ("metric".equals(command)) {
			//Get value of the metricsList object
			String val = metricsList.getSelectedItem().toString();
			switch(val) {
				case "Price":
					selectedMetric = "price";
					break;
				case "MarketCap":
					selectedMetric = "cap";
					break;
				case "Volume":
					selectedMetric = "vol";
					break;
				case "Coins in circulation":
					selectedMetric = "cic";
					break;
				case "Percent Change in Unit Price":
					selectedMetric = "price%";
					break;
				case "Percent Change in MarketCap":
					selectedMetric = "cap%";
					break;
				case "Percent Change in Volume":
					selectedMetric = "vol%";
					break;
				case "Percent Change of Coins in Circulation":
					selectedMetric = "cic%";
					break;
			}
			
		//If the user Selects an interval	
		}else if("interval".equals(command)) {
			selectedInterval = intervalList.getSelectedItem().toString();
		}
	}

	

}

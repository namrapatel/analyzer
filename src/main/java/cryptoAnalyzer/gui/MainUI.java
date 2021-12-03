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
import cryptoAnalyzer.gui.loginUi;


public class MainUI extends JFrame implements ActionListener{
	/**
	 * Summary of Changes:
	 * - Created Analysis interface which all analysis Types implement, all analysis types still need their actual analysis
	 * - Created factory class, still needs an implmenentation
	 * - Created Result, ResultData, and User Selection
	 * 
	 * TO DO - 
	 * I messed up on creation of ResultData class. I wrote is as handleing multiple cryptos when it should only handle since each 
	 * result object is tied to one coin. 
	 * Each Result object will have a ResultData object. We can then generate an object called ChartData which will
	 * have Object[] columnNames (I think this is just similar to what is in his dummy data) and Object[][]data which will be the combination of all ResultData 's from all the Results.
	 * The layout comes from his dummy data in DataVisualizationCreator
	 * 
	 * -Assign instance vars for metric, date, and interval.
	 * 
	 * Write all the performs for the different analysis
	 *  - use dataFetcher for this
	 * Write ChartData class
	 * 
	 *Implement All commented out code in this file
	 *
	 *How it works: 
	 *Basically, we create a list of userSelcetion objects, one for each coin (metric, date and interval stay the same for each).
	 * Then Run an analysis for each userSelection and store each Result in a list of Results. We then need to get 
	 * the ResultData object from each result and combine the Data into a 2d array similar to 
	 * the dummy data in his DataVisualizationCreator class. We will have to write a constructor for DataVisualizationCreator 
	 * that takes in a CharData object as its parameter. Essentailly, the ChartData object will just replace his dummy data that
	 * is hard coded in there
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static MainUI instance;
	private static loginUi ins;
	private JPanel stats, chartPanel, tablePanel;
	
	// Should be a reference to a separate object in actual implementation
	private List<String> selectedList;
	private JComboBox<String> metricsList;
	private JComboBox<String> intervalList;
	private JDatePickerImpl datePicker;
	private JTextArea selectedCryptoList;
	private JComboBox<String> cryptoList;

	private RestrictedCryptoList restrictedCoins;

	
	private List<Result> listOfResults; // need a list of results because we need to keep track of each analysis for each coin
	private List<UserSelection> listOfSelections; // need a list because we need to store a new selection for each coin in the list
	
	private String selectedMetric;
	private Date selectedDate;
	private String selectedInterval;
	
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	private MainUI() {
		
		// Set window title
		super("Crypto Analysis Tool");

		// Set top bar
		JLabel chooseCountryLabel = new JLabel("Choose a cryptocurrency: ");
		String[] cryptoNames = AvailableCryptoList.getInstance().getAvailableCryptos();
		restrictedCoins = new RestrictedCryptoList(cryptoNames);
		List <String> theCoins = restrictedCoins.getRestrictedCoins();
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
		            	selectedDate = dateFormatter.parse(dateReturn);
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
	
	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}

	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("refresh".equals(command)) {
			/*
			 * Call method to create UserSelections and add them to selection list
			 * this function will folow:
			 * for all coins theCoin in selectedCryptos{
			 * 		create new userSelection (theCoin, metric, date,interval);
			 * 		add to userSelectionList;
			 * }
			 * Then, 
			 * for all user Selections "theUserSelection" in Selection list{
			 * 		The Analysis = factory.create(theUserSelection)
			 * 		Result = theAnalysis.perform();
			 * 		add result to list of Results;
			 * }
			 * Then,
			
			 * For all results in list{
			 * 		get each results data and append to ChartData object. We'll figure out how to do this
			 * 
			 * }
			 * */
			stats.removeAll(); // keep this line
			
			//Remove these 2 lines in place of result.notifyObservers();
			DataVisualizationCreator creator = new DataVisualizationCreator(); 
			creator.createCharts();
		} else if ("add".equals(command)) {
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
		} else if ("remove".equals(command)) {
			selectedList.remove(cryptoList.getSelectedItem());
			String text = "";
			for (String crypto: selectedList)
				text += crypto + "\n";
			
			selectedCryptoList.setText(text);
		}else if ("metric".equals(command)) {
			//Get value of the metricsList object
			// Have if   based on value of metricsLists object,
			//Assign the selectedMetric instance variable to:
			//price,cap,vol,cic,price%,cap%,vol%, or cic %
			// The above are values that will get stored in each userSelection, which is used by factory class. This step does not get done here.
			
		}else if("interval".equals(command)) {
			//get value of intervalList object
			//Assign this value to the selectedInterval instance variable
			//This value will get stored in each user Selection
		}
	}

	

}

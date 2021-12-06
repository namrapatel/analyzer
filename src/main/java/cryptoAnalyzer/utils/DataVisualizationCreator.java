package cryptoAnalyzer.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cryptoAnalyzer.gui.MainUI;

import java.lang.Integer;

//Rewrite this file once we can pull data from API.
public class DataVisualizationCreator {
	
	private ChartData theData;
	private String theInterval;
	private String theMetric;
	
	//Write a constructor that takes in a ChartData object
	public DataVisualizationCreator(ChartData data) {
		theData = data;
		theInterval = theData.getUserSelection().getInterval();
		theMetric = renameMetric(theData.getUserSelection().getAnalysisType());
		
	}
	
	public void createCharts() {
		createTableOutput(); 
		createTimeSeries();
		createScatter();
		createBar();
	}
	private String renameMetric(String theMetric) {
		String toReturn = "";
		if(theMetric.equals("price")) {
			toReturn = "Price";
		}
		else if(theMetric.equals("cap")) {
			toReturn = "Market Capitalization";
		}
		else if(theMetric.equals("vol")) {
			toReturn = "Transaction Volume";
		}
		else if(theMetric.equals("cic")) {
			toReturn = "Coins in Circulation";
		}
		else if(theMetric.equals("price%")){
			toReturn = "% Change in Price";
		}
		else if(theMetric.equals("cap%")) {
			toReturn = "% Change in Market Capitalization";
		}
		else if(theMetric.equals("vol%")) {
			toReturn = "% Change in Transaction Volume";
		}
		else if(theMetric.equals("cic%")) {
			toReturn = "% Change in Coins in CirculationMarket Cap";
		}
		return toReturn;
	}
	private void createTableOutput() {
		// Dummy dates for demo purposes. These shoul come from selection menu
		Object[] columnNames = new Object[theData.getDates().length + 1];
		columnNames[0] = "Cryptocurrency";
		for(int i = 1; i < columnNames.length;i ++) {
			columnNames[i] = theData.getDates()[i-1].toString();
		}
		
		// Dummy data for demo purposes. These should come from actual fetcher
		
		Object[][] data = theData.getData();
		

		JTable table = new JTable(data, columnNames);
		//table.setPreferredSize(new Dimension(600, 300));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Daily Price Summary Table",
                TitledBorder.CENTER,
                TitledBorder.TOP));
		
	
		
		scrollPane.setPreferredSize(new Dimension(600, 300));
		table.setFillsViewportHeight(true);;
		
		MainUI.getInstance().updateStats(scrollPane);
	}

	private void createTimeSeries() {
		
		
		TimeSeries[] theSeries = new TimeSeries[theData.getRow()];
		
		for(int i = 0; i < theData.getRow(); i ++) {
			String seriesTitle = theData.getData()[i][0].toString();
			TimeSeries series = new TimeSeries(seriesTitle +" - "+theInterval);
			for(int j= 0; j < theData.getDates().length; j ++) {
				String theDate = theData.getDates()[j].toString();
				int day = Integer.parseInt(theDate.split("-")[0]);
				int month = Integer.parseInt(theDate.split("-")[1]);
				int year = Integer.parseInt(theDate.split("-")[2]);
				double value = (double) theData.getData()[i][j+1];
				series.add(new Day(day,month,year),value);
			}
			theSeries[i] = series;
			
		}
				

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(int i = 0; i < theSeries.length; i++) {
			dataset.addSeries(theSeries[i]);
		}
		

		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		
		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new LogAxis("Price(USD)"));

		//plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		//plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		//plot.mapDatasetToRangeAxis(2, 2);// 3rd dataset to 3rd y-axis
		
		JFreeChart chart = new JFreeChart(theInterval+" "+ theMetric+" Line Chart", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		
		MainUI.getInstance().updateStats(chartPanel);
	}
	
	private void createScatter() {
		TimeSeries[] theSeries = new TimeSeries[theData.getRow()];
		
		for(int i = 0; i < theData.getRow(); i ++) {
			String seriesTitle = theData.getData()[i][0].toString();
			TimeSeries series = new TimeSeries(seriesTitle +" - "+theInterval);
			for(int j= 0; j < theData.getDates().length; j ++) {
				String theDate = theData.getDates()[j].toString();
				int day = Integer.parseInt(theDate.split("-")[0]);
				int month = Integer.parseInt(theDate.split("-")[1]);
				int year = Integer.parseInt(theDate.split("-")[2]);
				double value = (double) theData.getData()[i][j+1];
				series.add(new Day(day,month,year),value);
			}
			theSeries[i] = series;
			
		}
				

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(int i = 0; i < theSeries.length; i++) {
			dataset.addSeries(theSeries[i]);
		}

		XYPlot plot = new XYPlot();
		XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new LogAxis("Price(USD)"));

		//plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		//plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
		
		JFreeChart scatterChart = new JFreeChart(theInterval+" "+ theMetric+" Scatter Chart",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}
	
	private void createBar() {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < theData.getRow(); i ++) {
			String coinName = theData.getData()[i][0].toString();
			for(int j= 0; j < theData.getDates().length; j ++) {
				String theDate = theData.getDates()[j].toString();
				double value = (double) theData.getData()[i][j+1];
				dataset.setValue(value, coinName, theDate);
			}
			
			
		}

		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Date");
		plot.setDomainAxis(domainAxis);
		LogAxis rangeAxis = new LogAxis("Price(USD)");
		rangeAxis.setRange(new Range(1.0, 70000.0));
		plot.setRangeAxis(rangeAxis);

		//plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
		//plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

		JFreeChart barChart = new JFreeChart(theInterval+" "+ theMetric+" Bar Chart", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}

}

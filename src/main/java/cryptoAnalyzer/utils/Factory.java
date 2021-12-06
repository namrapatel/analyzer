/**
 * Factory class used to get the requested type of analysis
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

public class Factory {
	/**
	 * Create class that takes the user's selected analysis type and performs the analysis 
	 * on each of the cryptos currently in the list over the specified interval
	 * @param users analysis selection
	 * @return data from requested analysis 
	 */
	public Analysis create(UserSelection selection) {
		if (selection.getAnalysisType().equals("price"))
			return new PriceAnalysis(selection);
		else if (selection.getAnalysisType().equals("cap"))
			return new MarketCapAnalysis(selection);
		else if (selection.getAnalysisType().equals("vol"))
			return new VolumeAnalysis(selection);
		else if (selection.getAnalysisType().equals("cic"))
			return new CicAnalysis(selection);
		else if (selection.getAnalysisType().equals("price%"))
			return new PercentPriceAnalysis(selection);
		else if (selection.getAnalysisType().equals("cap%"))
			return new PercentMarketCapAnalysis(selection);
		else if (selection.getAnalysisType().equals("vol%"))
			return new PercentVolumeAnalysis(selection);
		else if (selection.getAnalysisType().equals("cic%"))
			return new PercentCicAnalysis(selection);
		System.out.println("Could not create Analysis because of Invalid type!"); //Possibly change to error msg box?
		return null;
		
	}
}

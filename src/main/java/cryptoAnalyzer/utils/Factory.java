package cryptoAnalyzer.utils;

public class Factory {
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

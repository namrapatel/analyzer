/**
 * Class used to get data from CoinGecko API
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataFetcher {
	/**
	 * Get information for given coin on given date from coinGecko API stored in a json file
	 * @param id of the coin to get data for
	 * @param date to get data from
	 * @return json file with data from coingecko
	 */
	private JsonObject getDataForCrypto(String id, String date) {

		String urlString = String.format(
				"https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Something went wrong with the API call. System will now exit",
					"Fatal Error in API Call",JOptionPane.INFORMATION_MESSAGE);
			System.exit(-1);
		}
		return null;
	}
	
	/**
	 * Returns the price of a given coin on a given date
	 * @param id of the coin to get data for
	 * @param date to get the data from
	 * @return the price as a double
	 */
	public double getPriceForCoin(String id, String date) {
		double price = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
			price = currentPrice.get("cad").getAsDouble();
		}
		
		return price;
	}
	/**
	 * Returns the market cap of a given coin on a given date
	 * @param id of the coin to get data for
	 * @param date to get the data from
	 * @return the market cap as a double
	 */
	public double getMarketCapForCoin(String id, String date) {
		double marketCap = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
			marketCap = currentPrice.get("cad").getAsDouble();
		}
		
		return marketCap;
	}
	
	/**
	 * Returns the volume of a given coin on a given date
	 * @param id of the coin to get data for
	 * @param date to get the data from
	 * @return the volume as a double
	 */
	public double getVolumeForCoin(String id, String date) {
		double volume = 0.0;
		
		JsonObject jsonObject = getDataForCrypto(id, date);
		if (jsonObject != null) {
			JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
			JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
			volume = currentPrice.get("cad").getAsDouble();
		}
		
		return volume;
	}
	
	
}

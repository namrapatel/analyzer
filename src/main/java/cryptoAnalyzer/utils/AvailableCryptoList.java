package cryptoAnalyzer.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * The AvailableCryptoList class implements functionality that determines which tokens are available
 * to be read from the CoinGecko API and stores them with their name and ID.
 * 
 * @author	Matthew Cheverie
 * @author	Jack Di Falco
 * @author	Cole Duffy
 * @author	Namra Patel
 * @version	1.0
 * @since	1.0
 */
public class AvailableCryptoList {
	private static AvailableCryptoList instance = null; 
	private Map<String, String> availableCryptosMap = new HashMap<>();
	private List<String> availableCryptosList = new ArrayList<>();
	
	public static AvailableCryptoList getInstance() {
		if (instance == null)
			instance = new AvailableCryptoList();
		
		return instance;
	}
	
	private AvailableCryptoList() {
		findAvailableCryptos();
	}
	
	/**
	 * Checks CoinGecko's API for a list of available tokens that can be read, captures the JSON response from 
	 * CoinGecko and adds the respective token name to availableCryptosMap and availableCryptosList.
	 * 
	 * @param None.
	 * @return None.
	 */
	private void findAvailableCryptos() {
		String urlString = 
				"https://api.coingecko.com/api/v3/coins/markets" + 
						"?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false";
		
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
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				int size = jsonArray.size();
				
				String name, id;
				for (int i = 0; i < size; i++) {
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					name = object.get("name").getAsString();
					id = object.get("id").getAsString();
					
					availableCryptosMap.put(name, id);
					availableCryptosList.add(name);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
	}
	
	

	
	/**
	 * Getter method that turns availableCryptosList into an array and returns it.
	 * 
	 * @return An array of strings, where the strings are the names of the tokens available to
	 * be read from the CoinGecko API.
	 */
	public String[] getAvailableCryptos() {
		return availableCryptosList.toArray(new String[availableCryptosList.size()]);
	}
	
	
	/**
	 * Getter method that takes a token name and returns the ID of that token as it is 
	 * represented on CoinGecko.
	 * 
	 * @param cryptoName A token name represented as a string.
	 * @return Returns the ID of the token that was passed in the param as cryptoName, 
	 * from the availableCryptosMap.
	 */
	public String getCryptoID(String cryptoName) {
		return availableCryptosMap.get(cryptoName);
	}

}

package cryptoAnalyzer.utils;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * This class represents the list that limits the coins that users are allowed to search from.
 * We could not decided on which coins to limit because there were so many to choose from. 
 * We decided to make the restricted coins random each time the program is ran to introduce some variation
 * The restricted coins are printed to console for testing purposes.
 * @author Matthew Cheverie
 * @author Cole Duffy
 * @author Namra Patel
 * @author Jack DiFalco
 *
 */
public class RestrictedCryptoList {
	
	//Declare instance variables
	private String[] listOfAll;
	private List <String> restrictedList = new ArrayList<String>();
	private List<Integer> randomNums =  new ArrayList<Integer>();
	
	/**
	 * The Constructor for this class.
	 * @param activeCoins - The list of active coins
	 */
	public RestrictedCryptoList (String [] activeCoins) {
		listOfAll = activeCoins;
		findRestrictedList();
	}
	
	/**
	 * This function will find the list of restricted coins and generate the restrictedList variable
	 */
	private void findRestrictedList () {
		
		int size = listOfAll.length;
		int numberOfrandoms = (int)size/4;
		generateRandomNumber(size);
		
		for(int i =0; i<numberOfrandoms;i++) {
			String toAdd = listOfAll[randomNums.get(i)];
			restrictedList.add(i, toAdd);
		}
		
	}
	/**
	 * Getter function for the restrictedList variable
	 * @return List<String> the restrictedList variable
	 */
	public List<String> getRestrictedCoins() {
		return restrictedList;
	}
	
	/**
	 * This function will print the list to the console
	 */
	public void printList() {
		for(int i = 0; i < restrictedList.size(); i++) {
			System.out.println(restrictedList.get(i));
		}
	}
	/**
	 * This function will generate random numbers by creating a list of 
	 * integers equal to the number of active coins
	 * It will then shuffle the list which then will be used to pick active coins from "random"
	 * to limit
	 * @param size the number of active coins
	 */
	private void generateRandomNumber(int size) {
		for(int i=0; i < size; i++ ) {
			randomNums.add(i);
		}
		Collections.shuffle(randomNums);
	}
	
	
}

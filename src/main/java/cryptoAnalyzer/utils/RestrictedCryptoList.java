package cryptoAnalyzer.utils;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
public class RestrictedCryptoList {
	
	private String[] listOfAll;
	private List <String> restrictedList = new ArrayList<String>();
	private List<Integer> randomNums =  new ArrayList<Integer>();
	
	public RestrictedCryptoList (String [] activeCoins) {
		listOfAll = activeCoins;
		findRestrictedList();
	}
	
	
	private void findRestrictedList () {
		
		int size = listOfAll.length;
		int numberOfrandoms = (int)size/4;
		generateRandomNumber(size);
		
		for(int i =0; i<numberOfrandoms;i++) {
			String toAdd = listOfAll[randomNums.get(i)];
			restrictedList.add(i, toAdd);
		}
		
	}
	public List<String> getRestrictedCoins() {
		return restrictedList;
	}
	
	//for testing
	public void printList() {
		for(int i = 0; i < restrictedList.size(); i++) {
			System.out.println(restrictedList.get(i));
		}
	}
	private void generateRandomNumber(int size) {
		for(int i=0; i < size; i++ ) {
			randomNums.add(i);
		}
		Collections.shuffle(randomNums);
	}
	
	
}

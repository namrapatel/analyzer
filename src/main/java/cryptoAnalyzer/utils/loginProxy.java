package cryptoAnalyzer.utils;

import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * This class represents the login proxy that is used to verify logins 
 * @author Matthew Cheverie
 * @author Cole Duffy
 * @author Namra Patel
 * @author Jack DiFalco
 *
 */
public class loginProxy {
	
	//Declare the dictionary that stored the login information
	private Hashtable <String, String> loginDict;
	
	/**
	 * Constructor for this class
	 * Tries to load in the login info. If it can't. Throw exception and exit the program
	 */
	public loginProxy() {
		
		loginDict = new Hashtable <String, String>();
		try {
			loadLoginInfo();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Loads the login information from the file found in src/main/resources/loginInfo.txt
	 * @throws IOException Throws this is the file cannot be read for whatever reason.
	 */
	private void loadLoginInfo() throws IOException {
		InputStream is = this.fileAsIO("loginInfo.txt");
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader buff = new BufferedReader(reader);
		String line;
		while((line = buff.readLine()) != null) {
			String[] splitList = line.split(",");
			this.getLoginDict().put(splitList[0], splitList[1]);
			
		}
		is.close();
		
	}
	
	/**
	 * Getter for the dictionary object containing the login info
	 * @return An object of type Hashtable<String, String> representing the dictionary containing login info
	 */
	public Hashtable <String, String> getLoginDict(){
		return this.loginDict;
	}
	/**
	 * This method will validate if the given username and password exists in the login dictionary
	 * @param username The given username, String
	 * @param password The given password, String
	 * @return True if the login is valid, false if not
	 */
	public boolean validateLogin(String username, String password) {
		if(this.getLoginDict().containsKey(username)) {
			if(this.getLoginDict().get(username).equals(password))
				return true;
		}
		return false;
	}
	/**
	 * Function that will read the file containg the login information
	 * @param filename
	 * @return the InputStream representing the contents of our login file
	 */
	private InputStream fileAsIO(String filename) {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filename);
		if(stream == null) {
			throw new IllegalArgumentException(filename +"is not able to be found!");
		}
		return stream;
	}
}

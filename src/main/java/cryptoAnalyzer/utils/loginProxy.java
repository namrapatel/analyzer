package cryptoAnalyzer.utils;

import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class loginProxy {
	
	/**
	 * Declare attributes;
	 */
	private Hashtable <String, String> loginDict;
	
	public loginProxy(String loginFile) {
		
		
		loginDict = new Hashtable <String, String>();
	}
	
	public void loadLoginInfo(String filename) throws IOException {
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
	
	
	public Hashtable <String, String> getLoginDict(){
		return this.loginDict;
	}
	
	public boolean validateLogin(String username, String password) {
		if(this.getLoginDict().containsKey(username)) {
			if(this.getLoginDict().get(username) == password)
				return true;
		}
		return false;
	}
	private InputStream fileAsIO(String filename) {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filename);
		if(stream == null) {
			throw new IllegalArgumentException(filename +"is not able to be found!");
		}
		return stream;
	}
}

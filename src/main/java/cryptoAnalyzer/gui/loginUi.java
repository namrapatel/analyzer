package cryptoAnalyzer.gui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import cryptoAnalyzer.utils.loginProxy;
@SuppressWarnings("serial")
/**
 * CS 2212 
 * The loginUi class repesents the login ui window of this program.
 * This window allows users to enter a username and password that gets verified by the login proxy
 * The username password combos are stored in a text file called loginInfo.txt located in src/main/resources/
 * @author Matthew Cheverie
 * @author Cole Duffy
 * @author Namra Patel
 * @author Jack DiFalco
 *
 */
public class loginUi extends JFrame implements ActionListener{
	
	//Declare Java swing variables
	private static JTextField user;
	private static JPasswordField pass;
	JPanel thePanel;
	JFrame frame;
	
	//Declare class insatnces
	private static loginUi instance; 
	private loginProxy theproxy;
	
	//Declare data
	private String username;
	private String password;
	private Boolean status;
	private Boolean hasSubmitted;
	
	/**
	 * Constructor for this class.
	 */
	private loginUi() {
		//Create a loginProxy object
		status = false;
		hasSubmitted = false;
		theproxy = new loginProxy();
		thePanel = new JPanel();
		frame  = new JFrame();
		frame.setTitle("Login");
		frame.add(thePanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Window Stuff
		JLabel passLab, userLab;
		userLab = new JLabel("Username:");
		userLab.setBounds(new Rectangle(100, 30, 100, 30));
		userLab.setPreferredSize(new Dimension(100,30));
		thePanel.add(userLab);
		
		user = new JTextField();
		user.setBounds(new Rectangle(200, 30, 100, 30));
		user.setPreferredSize(new Dimension(100,30));
		thePanel.add(user);
		
		passLab = new JLabel("Password:");
		passLab.setBounds(new Rectangle(100, 60, 100, 30));
		passLab.setPreferredSize(new Dimension(100,30));
		thePanel.add(passLab);
		
		pass = new JPasswordField();
		pass.setBounds(new Rectangle(200, 60, 100, 30));
		pass.setPreferredSize(new Dimension(100,30));
		thePanel.add(pass);
		
		JButton button;
		button = new JButton("Login");
		button.setBounds(100, 110, 90, 25);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.BLACK);
		button.setActionCommand("verify");
		button.addActionListener(this);
		thePanel.add(button);
		
		
		
		
		
		
		
		
		
		
		
		
	}
	/**
	 * The method implemented from ActionListener that will be overridden
	 * This method will allow us to assign functionality to each of the Java.swing items such as buttons and selection lists
	 * @param e - an event of type ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setHasSubmitted(true);
		this.setUsername(user.getText());
		this.setPassword(String.valueOf(pass.getPassword()));
		String command = e.getActionCommand();
		if(command == "verify") {
			if(verifyLogin()) {
				this.setStatus(true);
				
			}
			else {
				this.setStatus(false);
				String errorMsg = "Invalid Username/password Combination! Program will now exit!";
				String Title = "Login Failed!";
				JOptionPane.showMessageDialog(null,errorMsg,Title,JOptionPane.INFORMATION_MESSAGE);
				System.exit(-1);
			}
			
		}
	}
	
	/**
	 * Method  to verify the login information using the login proxy
	 * @return True if the login infromation is valid. False if not
	 */
	public Boolean verifyLogin() {
		return this.getLoginProxy().validateLogin(this.getUsername(), this.getPassword());
	}
	
	/**
	 * Getter method for the username instance variable
	 * @return A string representing the username
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * Getter method for the password instance variable
	 * @return A string representing the password
	 */
	public String getPassword() {
		return this.password;
	}
	/**
	 * Setter for the username instance variable
	 * @param newUser The string representing the new username
	 */
	public void setUsername(String newUser) {
		this.username = newUser;
	}
	/**
	 * Setter for the password instance variable
	 * @param newPass The string representing the new password
	 */
	public void setPassword(String newPass) {
		this.password = newPass;
	}
	/**
	 * Getter method that gets the instance stored in this object. If null, create a new one
	 * @return an instance of type loginUi
	 */
	public static loginUi getInstance() {
		if (instance == null) {
			instance = new loginUi();
		}
		return instance;
	}
	/**
	 * Getter for the login proxy object
	 * @return a login proxy of type loginProxy
	 */
	public loginProxy getLoginProxy() {
		return this.theproxy;
	}
	/**
	 * Getter for the status instance variable
	 * @return the staus variable, Boolean
	 */
	public Boolean getStatus(){
		return this.status;
	}
	/**
	 * Setter for the status variable
	 * @param val  A true or false boolean
	 */
	public void setStatus(Boolean val) {
		this.status = val;
	}
	/**
	 * Setter for the hasSubmitted variable
	 * @param val  A true or false boolean
	 */
	public void setHasSubmitted(Boolean val) {
		this.hasSubmitted = val;
	}
	/**
	 * Getter for the hasSubmitted instance variable
	 * @return
	 */
	public Boolean getHasSubmitted() {
		return this.hasSubmitted;
	}
	/**
	 * getter for the jframe instance variable
	 * @return a jframe object representing the frame instance variable
	 */
	public JFrame getFrame() {
		return loginUi.getInstance().frame;
	}
	/**
	 * This function will display the login ui
	 */
	public void Display() {
		JFrame window = loginUi.getInstance().getFrame();
		window.setSize(400,200);
		window.pack();
		window.setVisible(true);
	}
	/**
	 * This function will a boolean based on whether the loginUi object was destroyed successfully or not
	 * @return True or False based on if the login window was destroyed
	 */
	public Boolean DestroyOnSucess() {
		Boolean Flag = true;
		try {
			this.getFrame().dispose();
		}
		catch (Exception e) {
			e.printStackTrace();
			Flag = false;
		}
		return Flag;
	}
	
	
	
}

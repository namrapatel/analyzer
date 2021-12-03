package cryptoAnalyzer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import cryptoAnalyzer.utils.loginProxy;
public class loginUi extends JFrame implements ActionListener{
	
	
	private static JTextField user;
	private static JPasswordField pass;
	private static loginUi instance; 
	private loginProxy theproxy;
	private String username;
	private String password;
	private Boolean status;
	private Boolean hasSubmitted;
	JPanel thePanel;
	JFrame frame;
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
	
	public Boolean verifyLogin() {
		return this.getLoginProxy().validateLogin(this.getUsername(), this.getPassword());
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setUsername(String newUser) {
		this.username = newUser;
	}
	public void setPassword(String newPass) {
		this.password = newPass;
	}
	public static loginUi getInstance() {
		if (instance == null) {
			instance = new loginUi();
		}
		return instance;
	}
	public loginProxy getLoginProxy() {
		return this.theproxy;
	}
	public Boolean getStatus(){
		return this.status;
	}
	public void setStatus(Boolean val) {
		this.status = val;
	}
	public void setHasSubmitted(Boolean val) {
		this.hasSubmitted = val;
	}
	public Boolean getHasSubmitted() {
		return this.hasSubmitted;
	}
	public JFrame getFrame() {
		return loginUi.getInstance().frame;
	}
	
	public void Display() {
		JFrame window = loginUi.getInstance().getFrame();
		window.setSize(400,200);
		window.pack();
		window.setVisible(true);
	}
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

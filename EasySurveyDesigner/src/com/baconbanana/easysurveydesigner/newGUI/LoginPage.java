package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends Window{
	
	private JPanel panel;
	private JTextField userNameTxf;
	private JTextField passwordTxf;
	private JButton loginBtn;

	public LoginPage(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		setFrameOptions();
	}
	
	private void initiWidgets(){
		//DBCreator.checkAndCreateTables();
		//create panel for widgets and layout
		getWindow().setResizable(false);
		panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints bagCon = new GridBagConstraints();
		bagCon.gridx = 0;
		//subject to change
		bagCon.gridy = 0;
		bagCon.gridheight = 1;
		bagCon.gridwidth = 3;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.anchor = GridBagConstraints.CENTER;
		bagCon.weightx = 1;
		bagCon.weighty = 0.3;
		
		JLabel welcome = new JLabel("<html><p style='font-size:x-large;text-align:center;'>Welcome to<br/><strong><i>Easy Survey Designer</i></strong></p></html>");
		panel.add(welcome, bagCon);
		
		//Creates label and text field to panel
		//TODO remove barry and xxx
		bagCon.gridy++;
		bagCon.gridwidth = 1;
		bagCon.weightx = 0;
		bagCon.weighty = 0.1;
		panel.add(new JLabel("User name"), bagCon);
		
		bagCon.gridy++;
		bagCon.gridwidth = 3;
		bagCon.weightx = 1;
		bagCon.fill = GridBagConstraints.HORIZONTAL;
		userNameTxf = new JTextField("Barry");
		panel.add(userNameTxf, bagCon);
		
		bagCon.gridy++;
		bagCon.gridwidth = 1;
		bagCon.weightx = 0;
		bagCon.fill = GridBagConstraints.NONE;
		panel.add(new JLabel("Password"), bagCon);
		
		bagCon.gridy++;
		bagCon.gridwidth = 3;
		bagCon.weightx = 1;
		bagCon.fill = GridBagConstraints.HORIZONTAL;
		passwordTxf = new JPasswordField("xxx");
		panel.add(passwordTxf, bagCon);
		
		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		//Creates and add login button
		loginBtn = new JButton("Log in");
		loginBtn.addActionListener(this);
		panel.add(loginBtn, bagCon);
		
		getWindow().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(loginBtn)){
			//TODO change to getpassword
			checkPassword(userNameTxf.getText(), passwordTxf.getText());
		}
		
		
	}
	//WHENEVER YOU click login you will you lucky thing
	public void checkPassword(String username, String password){                    
		//String sql = "SELECT * FROM Login WHERE Username = '" + username + "' AND Password = '" + password + "'";
		//if (DBOperation.existsRecord(sql)) {
				new Menu("Menu", 250, 300);
				getWindow().dispose();
		/*	}
			else {
				getWindow().dispose();
				new LoginPage("Login Page");
				System.out.println("you have fucked up!!");
			}*/
	}
	public static void main(String args[])
	{
		new LoginPage("Login Page", 300, 300);
	}
	
}

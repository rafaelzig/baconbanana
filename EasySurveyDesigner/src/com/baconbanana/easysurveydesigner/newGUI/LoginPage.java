package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBCreator;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
import com.baconbanana.easysurveydesigner.gui.MenuFrame;

public class LoginPage extends Window{
	
	private JPanel panel;
	private JTextField userNameField;
	private JTextField passwordField;
	private JButton loginButton;

	public LoginPage(String tit) {
		super(tit, 200, 200);
		initiWidgets();
		getWindow().pack();
	}
	
	private void initiWidgets(){
		DBCreator.checkAndCreateTables();
		//create panel for widgets and layout
		panel = new JPanel(new GridLayout(5,1));
		getWindow().add(panel);
		//Creates lable and textfeild to panel
		//TODO remove barry and xxx
		panel.add(new JLabel("User name"));
		userNameField = new JTextField("Barry");
		panel.add(userNameField);
		
		panel.add(new JLabel("Password"));
		passwordField = new JPasswordField("xxx");
		panel.add(passwordField);
		//Createas and add login button
		loginButton = new JButton("Log in");
		loginButton.addActionListener(this);
		panel.add(loginButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(loginButton)){
			//TODO change to getpassword
			checkPassword(userNameField.getText(), passwordField.getText());
		}
		
		
	}
	//WHENEVER YOU click login you will you lucky thing
	public void checkPassword(String username, String password){                    
		//String sql = "SELECT * FROM Login WHERE Username = '" + username + "' AND Password = '" + password + "'";
		//if (DBOperation.existsRecord(sql)) {
				new Menu("Menu", 400, 400);
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
		new LoginPage("Login Page");
	}
	
}

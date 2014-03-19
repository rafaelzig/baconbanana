package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBCreator;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
//Note for future pondering, SQLite does not have usernames or passwords so we will have to implement new security (encrypt files pos)
	public class LoginPage {
		public static JFrame loginPageFrame;
		static String username;
		static String password;
		//Create frame and set close ops
		public LoginPage()
		{
			loginPageFrame  = new JFrame("Login Page");
			loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//create database tables if they don't exist
			DBCreator.checkAndCreateTables();
			initLayout();
			loginPageFrame.setVisible(true);
			
		}
		//create and add layout items 
		private void initLayout()
		{
			JPanel panel = new JPanel(new GridLayout(5,1));
			loginPageFrame.add(panel);
			
			panel.add(new JLabel("User name"));
			final JTextField userNameField = new JTextField("Barry");
			panel.add(userNameField);
			
			panel.add(new JLabel("Password"));
			final JPasswordField passwordField = new JPasswordField("xxx");
			panel.add(passwordField);
			
			JButton loginButton = new JButton("Log in");
			panel.add(loginButton);
			
			loginPageFrame.setBounds(200,200,200,200);
			
			//create actionlistener for the button
			loginButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//String s = null;      *
					username = userNameField.getText();
					//TODO 	Change to getPassword
					password = passwordField.getText();
					//insertUser();
					/*
					DBOperation.checkPassword2();    *
					if (s == userNameField.getText()){
						new MenuFrame();
						loginPageFrame.dispose();
						System.out.println("Success");
					}
					else{
						loginPageFrame.dispose();
						new LoginPage();
						System.out.println("Fail");
					}
					*/ 
					checkPassword();            
					//This statement deletes the user after login. This is done to avoid error when you next open the software.
					//It will be useless once I will get special menu to create users in the future. 
					//String sql = "Login where Username='Barry';";
					//DBOperation.deleteRecord(sql);
					
					//new MenuFrame();
					//new DataTest();
					//System.out.print(getUserName()+getPassword());
					//loginPageFrame.dispose();
				}
			});
		
		}
		
		//method below adds user. It will have to be moved to different class in future but I wanted to hardcode user just to test it.
		public void insertUser(){
			String sql = "login VALUES ('Barry', 'xxx')";
			DBOperation.insertRecord(sql);
		}
		public void checkPassword(){                    
			String sql = "SELECT * FROM Login WHERE Username = '" + username + "' AND Password = '" + password + "'";
			
			
			
			if (DBOperation.existsRecord(sql)) {
					new MenuFrame();
					loginPageFrame.dispose();
					System.out.println("Good job");
				}
				else {
					loginPageFrame.dispose();
					new LoginPage();
					System.out.println("you have fucked up!!");
				}
				
			
		}
		
		
		/*
		public static String getUserName()
		
			return "user="+username;
		}
		public static String getPassword()
		{
			return "password="+password;
		}
		*/
		public static void main(String args[])
		{
			new LoginPage();
		}
	}

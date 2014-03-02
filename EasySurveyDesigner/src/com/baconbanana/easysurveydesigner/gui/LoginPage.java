package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class LoginPage {
	static JFrame loginPageFrame = new JFrame("Login Page");
	static String username;
	static String password;
	public LoginPage()
	{
		/*
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		     MetalLookAndFeel.setCurrentTheme(new TestStyle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//bla
		//kllk

		loginPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initLayout();
		loginPageFrame.setVisible(true);
		//JFrame.setDefaultLookAndFeelDecorated(true);
		
	}
	public void initLayout()
	{
		
		
		JPanel panel = new JPanel(new GridLayout(5,1));
		loginPageFrame.add(panel);
		panel.add(new JLabel("User name"));
		final JTextField userNameField = new JTextField("");
		panel.add(userNameField);
		panel.add(new JLabel("Password"));
		final JPasswordField jPasswordField = new JPasswordField();
		panel.add(jPasswordField);
		JButton loginButton = new JButton("Sing in");
		panel.add(loginButton);
		loginPageFrame.setBounds(200,200,200,200);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//new MainWindow(userNameField.getText());
				username = userNameField.getText();
				password = jPasswordField.getText();
				new EasySurveyFrame();
				new DataTest();
				System.out.print(userNameField.getText()+jPasswordField.getText());
				loginPageFrame.dispose();
			}
		});
	
}
	public static String getUserName()
	{
		return "user="+username;
	}
	public static String getPassword()
	{
		return "password"+password;
	}
	public static void main(String args[])
	{
		
		new LoginPage();
	}
}

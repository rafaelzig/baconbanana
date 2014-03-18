package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBCreator;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperationOld;
import com.baconbanana.easysurveydesigner.gui.MenuFrame;

public class LoginPage extends Window{
	
	private JPanel panel;
	private JTextField userNameTxf;
	private JTextField passwordTxf;
	private JButton loginBtn;

	public LoginPage(String tit) {
		super(tit, 200, 200);
		initiWidgets();
		getWindow().pack();
	}
	
	private void initiWidgets(){
		//DBCreator.checkAndCreateTables();
		//create panel for widgets and layout
		panel = new JPanel(new GridLayout(5,1));
		getWindow().add(panel);
		//Creates label and text field to panel
		//TODO remove barry and xxx
		panel.add(new JLabel("User name"));
		userNameTxf = new JTextField("Barry");
		panel.add(userNameTxf);
		
		panel.add(new JLabel("Password"));
		passwordTxf = new JPasswordField("xxx");
		panel.add(passwordTxf);
		//Creates and add login button
		loginBtn = new JButton("Log in");
		loginBtn.addActionListener(this);
		panel.add(loginBtn);
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
				new Menu("Menu", 300, 300);
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

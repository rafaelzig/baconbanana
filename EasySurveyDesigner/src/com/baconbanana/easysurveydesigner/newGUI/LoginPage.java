package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class LoginPage extends Window
{

	private JPanel panel;
	private JTextField userNameTxf;
	private JTextField passwordTxf;
	private JButton loginBtn;
	private JButton createUserBtn;
	private JButton deleteUserBtn;
	private DBController controller = null;

	public LoginPage(String tit, int width, int height)
	{
		super(tit, width, height);
		initiWidgets();
		initialiseConnection();
		setFrameOptions();
	}

	private void initialiseConnection()
	{
		try
		{
			try
			{
				controller = DBController.getInstance();
				controller.loadResources();
				controller.createAllTables();
			}
			catch (InvalidStateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if (controller != null)
					controller.close();
			}

		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(-1);
		}
	}

	private void initiWidgets()
	{
		// DBCreator.checkAndCreateTables();
		// create panel for widgets and layout
		getWindow().setResizable(false);
		panel = new JPanel(new GridBagLayout());

		GridBagConstraints bagCon = new GridBagConstraints();
		bagCon.gridx = 0;
		// subject to change
		bagCon.gridy = 0;
		bagCon.gridheight = 1;
		bagCon.gridwidth = 3;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.anchor = GridBagConstraints.CENTER;
		bagCon.weightx = 1;
		bagCon.weighty = 0.3;

		JLabel welcome = new JLabel(
				"<html><p style='font-size:x-large;text-align:center;'>Welcome to<br/><strong><i>Easy Survey Designer</i></strong></p></html>");
		panel.add(welcome, bagCon);

		// Creates label and text field to panel
		// TODO remove barry and xxx
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
		// Creates and add create user button
		createUserBtn = new JButton("Create new user");
		createUserBtn.addActionListener(this);
		panel.add(createUserBtn, bagCon);

		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		// Creates and add create user button
		deleteUserBtn = new JButton("Delete user");
		deleteUserBtn.addActionListener(this);
		panel.add(deleteUserBtn, bagCon);

		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		// Creates and add login button
		loginBtn = new JButton("Log in");
		loginBtn.addActionListener(this);
		panel.add(loginBtn, bagCon);

		getWindow().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		if (e.getSource().equals(loginBtn))
		{
			// TODO it appears that check password always give me true. It is
			// getting late and my brain is starting
			// to sleep so I will figure it out tomorrow or maybe some1 can help
			// me with it :P
//			if (checkPassword())
//			{
				new Menu("Menu", 250, 300);
				getWindow().dispose();
			}
//			else if (!checkPassword())
//			{
//				getWindow().dispose();
//				new LoginPage("Login Page", 300, 300);
//				System.out.println("you have fucked up!!");
//			}
//		}
		else if (e.getSource().equals(createUserBtn))
		{
			new CreateUser("Create new user", 300, 300);
			getWindow().dispose();
		}
		else if (e.getSource().equals(deleteUserBtn))
		{
			new DeleteUser("Delete user", 300, 300);
			getWindow().dispose();
		}
	}

	// WHENEVER YOU click login you will you lucky thing
	// public void checkPassword(){
	//
	// if (check = true) {
	// new Menu("Menu", 250, 300);
	// getWindow().dispose();
	// }
	// else {
	// getWindow().dispose();
	// new LoginPage("Login Page");
	// System.out.println("you have fucked up!!");
	// }
	// }
	public boolean checkPassword()
	{
		DBController controller = null;
		boolean check = false;
		String tableName = new String("Login");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Username");
		columns.add("Password");
		String condition = new String("Username='" + userNameTxf
				+ "' AND Password='" + passwordTxf + "'");
		try
		{
			try
			{
				controller = DBController.getInstance();
				controller.loadResources();
				if (controller.select(tableName, columns, condition).size() == 1)
				{
					check = true;
				}
				else if (controller.select(tableName, columns, condition)
						.size() != 1)
				{
					check = false;
				}
			}
			catch (InvalidStateException e1)
			{
				e1.printStackTrace();
			}
			finally
			{
				if (controller != null)
					controller.close();
			}
		}
		catch (SQLException | ClassNotFoundException e2)
		{

			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : "
					+ e2.getMessage());
			System.exit(-1);
		}
		return check;

	}

	public static void main(String args[])
	{
		new LoginPage("Login Page", 300, 300);
	}

}

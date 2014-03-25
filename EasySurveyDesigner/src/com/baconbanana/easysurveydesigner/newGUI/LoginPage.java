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
import javax.swing.WindowConstants;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
/**
 * The first window of the program which checks if user is allowed to use it
 * @author ZimS
 *
 */
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
			controller = DBController.getInstance();
			controller.createAllTables();
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
		getWindow().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel = new JPanel(new GridBagLayout());

		GridBagConstraints bagCon = new GridBagConstraints();
		
		panel.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 7, 0.3, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		panel.add(new JPanel(), LayoutController.summonCon(4, 0, 1, 7, 0.3, 1, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		

		JLabel welcome = new JLabel(
				"<html><p style='font-size:x-large;text-align:center;'>Welcome to<br/><strong><i>Easy Survey Designer</i></strong></p></html>");
		panel.add(welcome, LayoutController.summonCon(1, 0, 3, 1, 1, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE));

		// Creates label and text field to panel
		// TODO remove barry and xxx

		panel.add(new JLabel("User name"), LayoutController.summonCon(1, 1, 1, 1, 0, 0.1, GridBagConstraints.CENTER, GridBagConstraints.NONE));


		userNameTxf = new JTextField("Barry");
		panel.add(userNameTxf, LayoutController.summonCon(1, 2, 3, 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));


		bagCon.fill = GridBagConstraints.NONE;
		panel.add(new JLabel("Password"), LayoutController.summonCon(1, 3, 1, 1, 1, 0.1, GridBagConstraints.CENTER, bagCon.fill = GridBagConstraints.NONE));

		passwordTxf = new JPasswordField("xxx");
		panel.add(passwordTxf, LayoutController.summonCon(1, 4, 3, 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));

		// Creates and add create user button
		createUserBtn = new JButton("Create new user");
		createUserBtn.addActionListener(this);
		panel.add(createUserBtn, LayoutController.summonCon(1, 5, 3, 1, 1, 0.3, GridBagConstraints.CENTER, bagCon.fill = GridBagConstraints.NONE));

		// Creates and add create user button
		deleteUserBtn = new JButton("Delete user");
		deleteUserBtn.addActionListener(this);
		panel.add(deleteUserBtn, LayoutController.summonCon(1, 6, 3, 1, 1, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE));

		// Creates and add login button
		loginBtn = new JButton("Log in");
		loginBtn.addActionListener(this);
		panel.add(loginBtn, LayoutController.summonCon(1, 7, 3, 1, 1, 0.3, GridBagConstraints.CENTER, GridBagConstraints.NONE));
		
		getWindow().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
      
		if (e.getSource().equals(loginBtn))
		{
			
			if (checkPassword())
			{
				new Menu("Menu", 250, 300);
				getWindow().dispose();
			}else if (!checkPassword())
			{
				getWindow().dispose();
				new LoginPage("Login Page", 300, 300);
			}
		}
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
	public boolean checkPassword()
	{
		DBController controller = null;
		boolean check = false;
		String tableName = new String("Login");
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Username");
		columns.add("Password");
		String condition = new String("Username='" + userNameTxf.getText()
				+ "' AND Password='" + passwordTxf.getText() + "'");

			try
			{
				controller = DBController.getInstance();
				if (controller.exists(tableName, condition)){
					check = true;
				}
				else{
					check = false;
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

package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class DeleteUser extends Window {

	private JPanel panel;
	private JList<String> listOfUsers;
	private JButton deleteBtn;
	private JButton cancelBtn;
	
	final static DefaultListModel<String> listOfUsersModel = new DefaultListModel<String>();

	public DeleteUser(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		initiLayout();
		setFrameOptions();
		populateList();

	}

	private void initiWidgets() {
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

		bagCon.gridy++;
		bagCon.gridwidth = 1;
		bagCon.weightx = 0;
		bagCon.weighty = 0.1;
		listOfUsers = new JList<String>();
		listOfUsers.setModel(listOfUsersModel);
		listOfUsers.setVisible(true);
		panel.add(listOfUsers, bagCon);
		
		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		//Creates and add create user button
		deleteBtn = new JButton("Delete user");
		deleteBtn.addActionListener(this);
		panel.add(deleteBtn, bagCon);

		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		//Creates and add login button
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		panel.add(cancelBtn, bagCon);

		getWindow().add(panel);
		
	}

	public void populateList(){
		DBController controller = null;
		try {
			try {
				controller = DBController.getInstance();
				controller.loadResources();
				for (Object[] row : controller.selectAll("Login").subList(1, controller.selectAll("Login").size()))
				{
					for (int i = 0; i < row.length - 1; i += 2)
					{
						listOfUsersModel.addElement(row[i].toString());
					}

				}
			}catch (InvalidStateException e1) {
				e1.printStackTrace();
			}finally{
				if (controller != null)
					controller.close();
			}
		}catch (SQLException | ClassNotFoundException e2){
		
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
			System.exit(-1);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(deleteBtn)){
			String condition = new String("Username='" + listOfUsers.getSelectedValue() + "'");
			String tableName = new String("Login");
			DBController controller = null;
			try {
				try {
					controller = DBController.getInstance();
					controller.loadResources();
					controller.delete(tableName, condition);
				}catch (InvalidStateException e1) {
					e1.printStackTrace();
				}finally{
					if (controller != null)
						controller.close();
				}
			}catch (SQLException | ClassNotFoundException e2){
			
				e2.printStackTrace();
				System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
				System.exit(-1);
			}
			listOfUsersModel.removeAllElements();
			populateList();
		}else if(e.getSource().equals(cancelBtn)){
			new LoginPage("Create new user", 300, 300);
			listOfUsersModel.removeAllElements();
			getWindow().dispose();
		}
		
	}

}
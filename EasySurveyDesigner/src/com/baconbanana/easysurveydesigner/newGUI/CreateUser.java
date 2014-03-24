package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
/**
 * adding entry into database to hild new user and gui for it
 * @author ZimS
 *
 */
public class CreateUser extends Window{

	private JPanel panel;
	private JLabel createUserLbl;
	private JLabel createPasswordLbl;
	private JTextField createUserTxt;
	private JTextField createPasswordPf;
	private JButton okBtn;
	private JButton cancelBtn;

	public CreateUser(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		initiLayout();
		setFrameOptions();
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
		createUserLbl = new JLabel("Username");
		panel.add(createUserLbl, bagCon);

		bagCon.gridy++;
		bagCon.gridwidth = 3;
		bagCon.weightx = 1;
		bagCon.fill = GridBagConstraints.HORIZONTAL;
		createUserTxt = new JTextField();
		panel.add(createUserTxt, bagCon);

		bagCon.gridy++;
		bagCon.gridwidth = 1;
		bagCon.weightx = 0;
		bagCon.fill = GridBagConstraints.NONE;
		createPasswordLbl = new JLabel("Password");
		panel.add(createPasswordLbl, bagCon);

		bagCon.gridy++;
		bagCon.gridwidth = 3;
		bagCon.weightx = 1;
		bagCon.fill = GridBagConstraints.HORIZONTAL;
		createPasswordPf = new JPasswordField();
		panel.add(createPasswordPf, bagCon);

		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		//Creates and add create user button
		okBtn = new JButton("Create new user");
		okBtn.addActionListener(this);
		panel.add(okBtn, bagCon);

		bagCon.gridy++;
		bagCon.fill = GridBagConstraints.NONE;
		bagCon.weighty = 0.3;
		//Creates and add login button
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		panel.add(cancelBtn, bagCon);

		getWindow().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(okBtn)){
			
			DBController controller = null;
			String tableName = new String("Login");
			String[] values = new String[2];
			values[0] = ("'" + createUserTxt.getText() + "'");
			values[1] = ("'" + createPasswordPf.getText() + "'");
			if((createUserTxt.getText().trim().isEmpty()) || (createPasswordPf.getText().trim().isEmpty())){
				getWindow().dispose();
				new CreateUser("Create new user", 300, 300);
			}else{
			try {
				controller = DBController.getInstance();
				controller.insertInto(tableName, values);
			}catch (SQLException | ClassNotFoundException e2){
			
				e2.printStackTrace();
				System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
				System.exit(-1);
			}
			new LoginPage("Create new user", 300, 300);
			getWindow().dispose();
			}
		}
		else if(e.getSource().equals(cancelBtn)){
			new LoginPage("Create new user", 300, 300);
			getWindow().dispose();
		}

	}

}

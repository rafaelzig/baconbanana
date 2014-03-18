package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Menu extends Window{

	private JButton createSurvey;
	private JButton openSurvey;
	private JButton previewAnswers;
	private JButton getConnect;
	
	public Menu(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		getWindow().setSize(width, height);
	}
	
	private void initiWidgets(){
		//TODO fix button size
		getWindow().setLayout(new BorderLayout());
		getWindow().setResizable(false);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints btnCon = new GridBagConstraints();
		btnCon.gridx = 0;
		//subject to change
		btnCon.gridy = 0;
		btnCon.gridheight = 1;
		btnCon.gridwidth = 1;
		btnCon.fill = GridBagConstraints.HORIZONTAL;
		btnCon.anchor = GridBagConstraints.CENTER;
		
		
		createSurvey = new JButton ("Create new Survey");
		createSurvey.addActionListener(this);
		buttonPanel.add(createSurvey, btnCon);
		btnCon.gridy++;
		
		openSurvey = new JButton ("Open Survey"); 
		openSurvey.addActionListener(this);
		buttonPanel.add(openSurvey, btnCon);
		btnCon.gridy++;
		
		previewAnswers = new JButton ("<html>View Patients Answers</html>");
		previewAnswers.addActionListener(this);
		buttonPanel.add(previewAnswers, btnCon);
		btnCon.gridy++;
		
		getConnect = new JButton ("Connect to Device");
		getConnect.addActionListener(this);
		buttonPanel.add(getConnect, btnCon);
		
		getWindow().add(buttonPanel, BorderLayout.CENTER);
		
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource().equals(createSurvey)){
			new CreateSurvey("Create New Survey", true);
			getWindow().dispose();
		}else if(e.getSource().equals(openSurvey)){
			new SurveySelector("List of Surveys", true);
			getWindow().dispose();
		}
		else if(e.getSource().equals(previewAnswers)){
			new PatientSelector("List of Patients", true);
			getWindow().dispose();
		}
		else if(e.getSource().equals(getConnect)){
			new GetConnection("Connection Page");
			getWindow().dispose();
		}
		
		
		
	}

}

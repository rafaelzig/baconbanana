package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.ImportExport;
import com.baconbanana.easysurveydesigner.gui.ConnectionPage;


public class Menu extends Window{

	private JButton createSurvey;
	private JButton openSurvey;
	private JButton previewAnswers;
	private JButton getConnect;
	private JButton importBtn;
	private JButton exportBtn;
	
	
	public Menu(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		setFrameOptions();
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
		btnCon.gridy++;
		
		importBtn = new JButton ("Import");
		importBtn.addActionListener(this);
		buttonPanel.add(importBtn, btnCon);
		btnCon.gridy++;
		
		exportBtn = new JButton ("Export");
		exportBtn.addActionListener(this);
		buttonPanel.add(exportBtn, btnCon);
		
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
			//new SendSurvey("Connection Page", 400, 400);
			try {
				new ConnectionPage();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getWindow().dispose();
		}else if(e.getSource().equals(importBtn)){
			new ImportExport().startImport();
		}else if(e.getSource().equals(exportBtn)){
			new ImportExport().startExport();
		}
	
		
		
	}

}

package com.baconbanana.easysurveydesigner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * class to select a patient
 * @author ZimS
 *
 */
public class PatientSelector extends SQLWindow{
	
	private JLabel patientLbl;
	
	private JList<String> patientList;
	private JList<String> patientSurveyList;
	private SQLList patientListModel;
	private SQLList patientSurveyListModel;
	
	private JButton selectBtn;
	private JButton cancelBtn;
	
	private ListSelectionModel patientSelectionModel;
	
	public PatientSelector(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
	}
	
	private void initiWidgets() {
		
		patientListModel = new SQLList("Patient", 0, "Name");
		patientList =new JList<String>(patientListModel);
		patientSurveyListModel = new SQLList("Patient NATURAL JOIN Patient_Survey", "Name =" + DBController.appendApo(patientList.getSelectedValue()), 0,"PatientID", "Name", "Survey");
		patientSurveyList = new JList<String>(patientSurveyListModel);
		patientListModel.getData();
		patientSelectionModel=patientList.getSelectionModel();
		
		getWindow().setLayout(new BorderLayout());
		patientLbl = new JLabel("List of Patients");
		getWindow().add(patientLbl, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		centerPanel.add(new JScrollPane(patientList));
		patientList.setBorder(getBorder());
		centerPanel.add(new JScrollPane(patientSurveyList));
		patientSurveyList.setBorder(getBorder());
		getWindow().add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new GridLayout(1,2));
		selectBtn = new JButton("Select");
		southPanel.add(selectBtn);
		cancelBtn = new JButton("Cancel");
		southPanel.add(cancelBtn);
		getWindow().add(southPanel, BorderLayout.SOUTH);
		
		patientSelectionModel.addListSelectionListener(this);
		selectBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		setFrameOptions();
		// TODO FIX PACK!!!!!!!
		getWindow().pack();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(selectBtn)){
			if (!(patientSurveyList.getSelectedValue() == null)){
				
			}
		}
		else if (e.getSource().equals(cancelBtn)){
		getWindow().dispose();
		new Menu("Menu", 250, 300);
		}
	}
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(patientSelectionModel) && patientSelectionModel.getValueIsAdjusting() == false){
			//could change to templatelist.getselecteditem
			patientSurveyListModel = new SQLList("Patient NATURAL JOIN Patient_Survey", "Name =" 
			+ DBController.appendApo(patientList.getSelectedValue()), 2,"PatientID", "Name", "Survey");
			patientSurveyListModel.getData();
			populateList(patientSurveyList, patientSurveyListModel);
			
		}
	}

}
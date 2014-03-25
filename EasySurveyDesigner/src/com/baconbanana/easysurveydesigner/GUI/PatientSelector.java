package com.baconbanana.easysurveydesigner.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * class to select a patient
 * @author ZimS
 *
 */
public class PatientSelector extends Window{
	
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
		
		patientListModel = new SQLList("Patient", 0, "Patient");
		patientList =new JList<String>(patientListModel);
		patientSurveyListModel = new SQLList("Patient NATURAL JOIN Patient_Survey", "Patient =" + DBController.appendApo(patientList.getSelectedValue()), 0,"PatientID", "Patient", "Survey");
		patientSurveyList = new JList<String>(patientSurveyListModel);
		patientListModel.getData();
		patientSelectionModel=patientList.getSelectionModel();
		
		getWindow().setLayout(new BorderLayout());
		patientLbl = new JLabel("List of Patients");
		getWindow().add(patientLbl, BorderLayout.NORTH);
		JPanel centerPanel = new JPanel();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

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
	
	public PatientSelector(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
	}
	
	private void initiWidgets() {
		
		surveyModel = new SQLList("Survey", 0,"Survey");
		surveyList =new JList<String>(surveyModel);
		questionModel = new SQLList("Survey_Template NATURAL JOIN Template NATURAL JOIN Question",0,"Content");
		questionList = new JList<String>(questionModel);
		surveyModel.getData();
		surveySelectionModel=surveyList.getSelectionModel();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package com.baconbanana.easysurveydesigner.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class PatientAnswers extends SQLWindow{
	
	private JList<String> patientQuestionList;
	private JList<String> patientAnswersList;
	private SQLList patientQuestionListModel;
	private SQLList patientAnswersListModel;
	
	private JButton cancelBtn;
	
	private ListSelectionModel patientSelectionModel;
	
	protected PatientSelector patientSurvey;

	public PatientAnswers(String tit, int width, int height, PatientSelector surveySelected) {
		super(tit, width, height);
		patientSurvey = surveySelected;
		initiWidgets();
	}


	private void initiWidgets() {
		patientQuestionListModel = new SQLList("Survey_Template NATURAL JOIN Template NATURAL JOIN Question", "Survey=" + DBController.appendApo
				(patientSurvey.getPatientSurveyList().getSelectedValue()), 0, "Content");
		patientQuestionListModel.getData();
		patientQuestionList =new JList<String>(getPatientQuestionListModel());
		
		patientAnswersListModel = new SQLList("Patient NATURAL JOIN Patient_Survey", "Name =" + DBController.appendApo(patientQuestionList.getSelectedValue()), 0,"PatientID", "Name", "Survey");
		patientAnswersList = new JList<String>(patientAnswersListModel);
//		patientQuestionListModel.getData();
//		patientSelectionModel=patientQuestionList.getSelectionModel();
		
		getWindow().setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		centerPanel.add(new JScrollPane(patientQuestionList));
		patientQuestionList.setBorder(getBorder());
		centerPanel.add(new JScrollPane(patientAnswersList));
		patientAnswersList.setBorder(getBorder());
		getWindow().add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new GridLayout(1,1));
		cancelBtn = new JButton("Cancel");
		southPanel.add(cancelBtn);
		getWindow().add(southPanel, BorderLayout.SOUTH);
		
//		patientSelectionModel.addListSelectionListener(this);
		cancelBtn.addActionListener(this);
		
		setFrameOptions();
		// TODO FIX PACK!!!!!!!
		getWindow().pack();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(cancelBtn)){
			getWindow().dispose();
			new PatientSelector("List of Patients", 800, 800);
			}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public SQLList getPatientQuestionListModel() {
		return patientQuestionListModel;
	}

	public void setPatientQuestionListModel(SQLList patientQuestionListModel) {
		this.patientQuestionListModel = patientQuestionListModel;
	}

}

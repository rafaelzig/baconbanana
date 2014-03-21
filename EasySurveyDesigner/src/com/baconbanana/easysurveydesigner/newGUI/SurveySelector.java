package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.Table;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class SurveySelector extends SQLWindow {
	private  JList<String> surveyList = new JList<String>();
	private final  DefaultListModel<String> surveyListModel = new DefaultListModel<String>();

	private  JTextField nameOfSurveyTxf;
	public SurveySelector(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initLayout();
		setFrameOptions();
		initiLayout();
		
			}

	public void initLayout()
	{
		getWindow().setLayout(new BorderLayout());
		nameOfSurveyTxf = new JTextField("Type name for this survey here");
		JButton onlyOneDuckingButtonNoOneEverGoingToLookAt = new JButton("onlyOneDuckingButtonNoOneEverGoingToLookAt");
		getWindow().add(surveyList,BorderLayout.CENTER);
		getWindow().add(onlyOneDuckingButtonNoOneEverGoingToLookAt,BorderLayout.SOUTH);
		getWindow().add(nameOfSurveyTxf,BorderLayout.NORTH);
		getSurveyList().setBorder(getBorder());
		SQLList surveyModel = new SQLList(Table.SURVEY.getName(), new String[] {"Survey"} , 0);
		populateList(surveyList, surveyModel);
		setFrameOptions();
		
		
	}
	public  DefaultListModel<String> getSurveyListModel() {
		return surveyListModel;
	}
	public  JList<String> getSurveyList() {
		return surveyList;
	}
	public  void setSurveyList(JList<String>surveyList) {
		this.surveyList = surveyList;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setList(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

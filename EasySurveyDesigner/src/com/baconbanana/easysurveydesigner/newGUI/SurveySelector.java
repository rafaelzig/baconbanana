package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class SurveySelector extends SQLWindow implements ActionListener {
	private  JList<String> surveyList;
    private SQLList surveyModel;
    private JList<String>questionList;
    private SQLList questionModel;
	private  JTextField nameOfSurveyTxf;
	private ListSelectionModel surveySelectionModel;
	private JButton deleteButton = new JButton("Delete");
	JButton onlyOneDuckingButtonNoOneEverGoingToLookAt = new JButton("onlyOneDuckingButtonNoOneEverGoingToLookAt");
	public SurveySelector(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initLayout();
 
			}

	public void initLayout()
	{
		surveyModel = new SQLList("Survey", 0,"Survey");
		surveyList =new JList<String>(surveyModel);
		questionModel = new SQLList("Survey_Template NATURAL JOIN Template NATURAL JOIN Question",0,"Content");
		questionList = new JList<String>(questionModel);
		surveyModel.getData();
		surveySelectionModel=surveyList.getSelectionModel();
		
		getWindow().setLayout(new BorderLayout());
		nameOfSurveyTxf = new JTextField("Type name for this survey here");
		JPanel centralPanel = new JPanel(new GridLayout(1, 2));
		JPanel bottomPanel = new JPanel(new GridLayout(1,2));
		
		centralPanel.add(new JScrollPane(surveyList));
		centralPanel.add(new JScrollPane(questionList));
		getWindow().add(centralPanel,BorderLayout.CENTER);
		getWindow().add(bottomPanel,BorderLayout.SOUTH);
		
		bottomPanel.add(onlyOneDuckingButtonNoOneEverGoingToLookAt);
		bottomPanel.add(deleteButton);
		getWindow().add(nameOfSurveyTxf,BorderLayout.NORTH);
		getSurveyList().setBorder(getBorder());
		
		System.out.println(surveyModel.getSize());
		
		surveySelectionModel.addListSelectionListener(this);
		onlyOneDuckingButtonNoOneEverGoingToLookAt.addActionListener(this);
		deleteButton.addActionListener(this);
		
		//SQLList surveyModel = new SQLList(Table.SURVEY.getName(), new String[] {"Survey"} , 0);
	//	populateList(surveyList, surveyModel);
		setFrameOptions();
		// TODO FIX PACK!!!!!!!
		getWindow().pack();
	}
	public  SQLList getSurveyListModel() {
		return surveyModel;
	}
	public  JList<String> getSurveyList() {
		return surveyList;
	}
	public  void setSurveyList(JList<String>surveyList) {
		this.surveyList = surveyList;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(onlyOneDuckingButtonNoOneEverGoingToLookAt)){
		CreateSurvey newOne =  new CreateSurvey(surveyList.getSelectedValue(), true);
		newOne.getSurveyPrevModel().getData("Survey_Template", "Survey = " + DBController.appendApo(surveyList.getSelectedValue()), 1, "Survey", "Template");
		}
		else if (e.getSource().equals(deleteButton))
		{
			try {
				DBController.getInstance().delete("Survey","Survey="+ DBController.appendApo(surveyList.getSelectedValue()));
				DBController.getInstance().delete("Survey_Template","Survey="+ DBController.appendApo(surveyList.getSelectedValue()));
				surveyModel.getData();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
//	    SQLList surveyCreateModel = new SQLList("Survey_Template", "Survey=" + DBController.appendApo
//				(surveyModel.getId(surveyList.getSelectedIndex())),0,"Template");
//	    surveyCreateModel.getData();
		
		}
	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource().equals(surveySelectionModel) && surveySelectionModel.getValueIsAdjusting() == false){
			//could change to templatelist.getselecteditem
			questionModel = new SQLList("Survey_Template NATURAL JOIN Template NATURAL JOIN Question", "Survey=" + DBController.appendApo
					(surveyModel.getId(surveyList.getSelectedIndex())), 0, "Content");
			populateList(questionList, questionModel);
			questionModel.getData();
			System.out.println(surveyList.getSelectedIndex());
		}
		
	}

}

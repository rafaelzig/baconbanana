package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public class CreateSurvey extends SQLWindow{

	private static JList<String> templateList;
	private JList<String> templatePrevList;
	private JList<String> surveyPrevList;
	
	private ListSelectionModel templatelsm;
	//private ListSelectionModel templatePrevlsm;
	
	private SQLList templateModel;
	private SQLList templatePrevModel;
	private SQLList surveyPrevModel;

	private JButton addBtn;
	private JButton editBtn;
	private JButton deleteBtn;
	private JButton moveBtn;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JButton sendBtn;
	
		
	public CreateSurvey(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		initiWidgets();
	}
	
	public void initiWidgets(){
		//TODO ask Becka how to comment this all nice
		//TODO change layout here to make it look better
		addBtn = new JButton("Add");
		editBtn = new JButton("Edit");
		deleteBtn = new JButton("Delete");
		moveBtn = new JButton("Move");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		sendBtn = new JButton("Send");
		
		JLabel templatesLbl = new JLabel("List of templates");
		JLabel templatePrevLbl = new JLabel("Template preview");
		JLabel surveyPrevLbl = new JLabel("Survey preview");
		
		SQLList templateModel = new SQLList("Template", 0 , "Template", "QuestionID");
		//SQLList surveyPrevModel = new SQLList("Template", new String[] {"Template"} , 0);
		
		templateList = new JList<String>(templateModel);
		templatePrevList = new JList<String>(templateModel);
		surveyPrevList = new JList<String>();
		
		populateList(templateList, templateModel);
		
		templatelsm = templateList.getSelectionModel();
		//templatePrevlsm = templatePrevList.getSelectionModel();
		
		getWindow().setLayout(new BorderLayout());
		
		JPanel jpTemplates = new JPanel(new BorderLayout());
		getWindow().add(jpTemplates, BorderLayout.WEST);

		jpTemplates.add(templatesLbl, BorderLayout.NORTH);
		jpTemplates.add(templateList, BorderLayout.CENTER);
		templateList.setBorder(getBorder());
			
		JPanel jpTemplatesButtons = new JPanel(new FlowLayout());
		jpTemplates.add(jpTemplatesButtons, BorderLayout.SOUTH);
		jpTemplatesButtons.add(addBtn);
		addBtn.addActionListener(this);
		jpTemplatesButtons.add(editBtn);
		editBtn.addActionListener(this);
		jpTemplatesButtons.add(deleteBtn);
		deleteBtn.addActionListener(this);
		jpTemplatesButtons.add(moveBtn);
		moveBtn.addActionListener(this);
		
		JPanel jpTemplatesPreview = new JPanel(new BorderLayout());
		getWindow().add(jpTemplatesPreview, BorderLayout.CENTER);

		jpTemplatesPreview.add(templatePrevLbl, BorderLayout.NORTH);
		jpTemplatesPreview.add(templatePrevList, BorderLayout.CENTER);
		templatePrevList.setBorder(getBorder());
		
		JPanel jpQuestionsPreview = new JPanel(new BorderLayout());
		getWindow().add(jpQuestionsPreview, BorderLayout.SOUTH);

		jpQuestionsPreview.add(surveyPrevLbl, BorderLayout.NORTH);
		jpQuestionsPreview.add(surveyPrevList, BorderLayout.CENTER);
		surveyPrevList.setBorder(getBorder());
		
		JPanel jpQuestionsButton = new JPanel(new FlowLayout());
		jpQuestionsPreview.add(jpQuestionsButton, BorderLayout.SOUTH);
		jpQuestionsButton.add(saveBtn);
		saveBtn.addActionListener(this);
		jpQuestionsButton.add(cancelBtn);
		cancelBtn.addActionListener(this);
		jpQuestionsButton.add(sendBtn);
		sendBtn.addActionListener(this);
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(addBtn)){
			new AddTemplate("Create New Template", 800, 500);
			//TODO We need to either get rid of disabling previous windows or change it so it will enable them back again when u close current window or press cancel but...
			//	I am (Matt) to dumb to figure it out and I dont want to waste too much time on that because it is not that important at the moment :)
			//getWindow().setEnabled(false);
			
		}else if(e.getSource().equals(editBtn)){
			//TODO edit
		}
		else if(e.getSource().equals(deleteBtn)){
			//TODO delete
		}
		else if(e.getSource().equals(moveBtn)){
			//TODO move
		}
		else if(e.getSource().equals(saveBtn)){
			//TODO save
		}else if(e.getSource().equals(cancelBtn)){
			new Menu("Menu", 400, 400);
			getWindow().dispose();
		}
		else if(e.getSource().equals(sendBtn)){
			//TODO send
		}
		
	}

	@Override
	public void setList(ListSelectionEvent e) {
		if(e.getSource().equals(templatelsm)){
			templatePrevModel = new SQLList("Template NATURAL JOIN Question", "QuestionID=" + templateModel.getId(e.getFirstIndex()), 0, "Question");
			populateList(templatePrevList, templatePrevModel);
			
		}		
	}

}

package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

public class CreateSurvey extends SQLWindow{

	private static JList<String> templateList;
	private JList<String> templatePrevList;
	private JList<String> surveyPrevList;

	private JButton addBtn;
	private JButton editBtn;
	private JButton deleteBtn;
	private JButton moveBtn;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JButton sendBtn;
		
	final static DefaultListModel<String> myModel1 = new DefaultListModel<String>();
	public CreateSurvey(String tit, boolean fullScreen) {
		super(tit, fullScreen);
		// TODO Auto-generated constructor stub
	}
	
	public void initiWidgets(){
		//TODO ask Becka how to comment this all nice
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
		
		templateList = new JList<String>();
		templatePrevList = new JList<String>();
		surveyPrevList = new JList<String>();
		
		getWindow().setLayout(new BorderLayout());
		
		JPanel jpTemplates = new JPanel(new BorderLayout());
		getWindow().add(jpTemplates, BorderLayout.WEST);

		jpTemplates.add(templatesLbl, BorderLayout.NORTH);
		jpTemplates.add(templateList, BorderLayout.CENTER);
		templateList.setBorder(getBorder());
			
		JPanel jpTemplatesButtons = new JPanel(new FlowLayout());
		jpTemplates.add(jpTemplatesButtons, BorderLayout.SOUTH);
		jpTemplatesButtons.add(addBtn);
		jpTemplatesButtons.add(editBtn);
		jpTemplatesButtons.add(deleteBtn);
		jpTemplatesButtons.add(moveBtn);
		
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
		jpQuestionsButton.add(cancelBtn);
		jpQuestionsButton.add(sendBtn);
		
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(addBtn)){
			new AddTemplate("Create New Template", true);
			getWindow().dispose();
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

}

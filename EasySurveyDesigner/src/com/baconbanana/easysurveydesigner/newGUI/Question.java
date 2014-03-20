package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextArea;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

public class Question extends SQLWindow{

	private QuestionType questionType;
	private JTextArea questionTxa;
	private JButton saveBtn;
	private JButton cancelBtn;

	public Question(String tit, boolean fullScreen) {
		super(tit, fullScreen);
	}

	public void initiWidgets(){
		getWindow().setLayout(new BorderLayout());
		
		//Question name and data
		// --------------center of window---------------------
		setQuestionTxa(new JTextArea("Type your question here"));
		getQuestionTxa().setPreferredSize(new Dimension(800, 280));
		getQuestionTxa().setBorder(getBorder());
		getWindow().add(getQuestionTxa(), BorderLayout.CENTER);
		// ---------------------------------------------------
		

	}
	//This method adds general functionality to each cancel button in every type of the question.
	public void cancelQuestion(){
		new AddTemplate("Create New Template", 500, 800);
		getWindow().dispose();
	}
	
	public void saveQuestion(){
		AddTemplate.getTemplatelistmodel().addElement(getQuestionTxa().getText() + "("
				+ getQuestionType() + ")");
		AddTemplate.getTemplateList().setModel(AddTemplate.getTemplatelistmodel());
		
		QuestionType Type = getQuestionType();
		
	
		new AddTemplate("Create New Template", 500, 800);
		getWindow().dispose();
	}

	public JTextArea getQuestionTxa() {
		return questionTxa;
	}

	public void setQuestionTxa(JTextArea questionTxa) {
		this.questionTxa = questionTxa;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	
	public JButton getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(JButton cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(JButton saveBtn) {
		this.saveBtn = saveBtn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}

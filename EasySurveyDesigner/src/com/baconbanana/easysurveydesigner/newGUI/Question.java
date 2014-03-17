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
		initiWidgets();
	}

	public void initiWidgets(){
		getWindow().setLayout(new BorderLayout());
		
		//Question name and data
		// --------------center of window---------------------
		questionTxa = new JTextArea("Type your question here");
		questionTxa.setPreferredSize(new Dimension(800, 280));
		questionTxa.setBorder(getBorder());
		getWindow().add(questionTxa, BorderLayout.CENTER);
		// ---------------------------------------------------

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}

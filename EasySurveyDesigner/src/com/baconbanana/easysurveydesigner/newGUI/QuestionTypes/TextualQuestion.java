package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class TextualQuestion extends OpenQuestion{
	
	private String answerText;
	
	public TextualQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		answerText = "Type your answer here";
		answerTxa = JOptionPane.showInputDialog(null, "Enter Template Name : ", "Name Template", 1);
		t.getListModel().insertElement("Question", "null", answerTxa, QuestionType.TEXTUAL.toString());
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.TEXTUAL);
		}
	}
}

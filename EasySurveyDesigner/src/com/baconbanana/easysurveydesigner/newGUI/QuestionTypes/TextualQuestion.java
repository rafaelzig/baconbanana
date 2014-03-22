package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class TextualQuestion extends OpenQuestion{
	
	private String answerTxt;
	private DBController dbCon;
	
	public TextualQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		
		answerTxt= JOptionPane.showInputDialog(null, "Enter Textual Question:", "New Textual Question", 1);
		
		int questId = 0;
		try {
			dbCon = DBController.getInstance();
			questId = dbCon.insertInto("Question", "null", DBController.appendApo(answerTxt), DBController.appendApo(QuestionType.NUMERICAL.toString()));
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.getListModel().insertElement("Template", DBController.appendApo(t.getTemplateName()), String.valueOf(questId));
		t.getListModel().getData("Template","Template=" + DBController.appendApo(t.getTemplateName()), 0, "Template", "QuestionID");
		getWindow().dispose();
	}
	public void actionPerformed(ActionEvent e) {
	}
}

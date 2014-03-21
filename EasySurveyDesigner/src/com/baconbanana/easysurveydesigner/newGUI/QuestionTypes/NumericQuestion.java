package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;

public class NumericQuestion extends OpenQuestion{
	
	String answerText;
	
	public NumericQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		answerText = "Type number here";
		answerTxa = JOptionPane.showInputDialog(null, "Enter Numeric Question : ", "Name Template", 1);
		DBController dbCon;
		int questId = 0;
		try {
			dbCon = DBController.getInstance();
			dbCon.loadResources();
			questId = dbCon.insertInto("Question", "null", DBController.appendApo(answerText), DBController.appendApo(QuestionType.NUMERICAL.toString()));
		} catch (SQLException | InvalidStateException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.getList().insertElement("Template", DBController.appendApo(t.getTemplateName()), String.valueOf(questId));
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestionOq(QuestionType.NUMERICAL);
		}
	}
}

package com.baconbanana.easysurveydesigner.GUI.QuestionTypes;
/**
 * model for Numeric Questions
 */
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.GUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.GUI.Template;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.QuestionType;

public class NumericQuestion extends OpenQuestion{
	
	private String answerTxt;
	private DBController dbCon;
	
	public NumericQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.NUMERICAL);
		answerTxt = JOptionPane.showInputDialog(null, "Enter Numeric Question : ", "New Numeric Question", 1);
		
		int questId = 0;
		try {
			dbCon = DBController.getInstance();
			questId = t.getListModel().insertElement("Question", "null", DBController.appendApo(answerTxt), DBController.appendApo(QuestionType.NUMERICAL.toString()));
			dbCon.insertInto("Template", DBController.appendApo(t.getTemplateName()), String.valueOf(questId));
			t.getListModel().getData();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		t.getListModel().getData();

		getWindow().dispose();
	}
}

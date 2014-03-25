package com.baconbanana.easysurveydesigner.GUI.QuestionTypes;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.GUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.GUI.Template;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
/**
 * model for Textual Questions
 * @author ZimS
 *
 */
public class TextualQuestion extends OpenQuestion{
	
	private String answerTxt;
	private DBController dbCon;
	
	public TextualQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		
		answerTxt= JOptionPane.showInputDialog(null, "Enter Textual Question:", "New Textual Question", 1);
		
		int questId = 0;
		try {
			dbCon = DBController.getInstance();
			questId = dbCon.insertInto("Question", "null", DBController.appendApo(answerTxt), DBController.appendApo(QuestionType.TEXTUAL.toString()));
			dbCon.insertInto("Template", DBController.appendApo(t.getTemplateName()), String.valueOf(questId));
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		t.getListModel().getData();

		getWindow().dispose();
	}
	
	public void actionPerformed(ActionEvent e) {
	}
}

package com.baconbanana.easysurveydesigner.newGUI.QuestionTypes;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.newGUI.OpenQuestion;
import com.baconbanana.easysurveydesigner.newGUI.Template;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
/**
 * model for question Types
 * @author ZimS
 *
 */
public class DateQuestion extends OpenQuestion{
	
	private String answerTxt;
	private DBController dbCon;
	
	public DateQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		
		answerTxt = JOptionPane.showInputDialog(null, "Enter Date Question:", "Add Date Question", 1);
		
		int questId = 0;
		try {
			dbCon = DBController.getInstance();
			questId = dbCon.insertInto("Question", "null", DBController.appendApo(answerTxt), DBController.appendApo(QuestionType.DATE.toString()));
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

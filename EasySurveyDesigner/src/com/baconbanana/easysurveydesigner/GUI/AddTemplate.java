package com.baconbanana.easysurveydesigner.GUI;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.DateQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.NumericQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.RatingQuestion;
import com.baconbanana.easysurveydesigner.GUI.QuestionTypes.TextualQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveyfunctions.models.QuestionType;


/**
 * class for Adding Templates
 * @author ZimS
 *
 */


public class AddTemplate extends Template{


	public AddTemplate(String tit, int width, int height, Survey cs) {
		super(tit, width, height);
		createSurvey = cs;
		initiWidgets();
		enableTemplateNameRequester(false);
	}
	/**
	 * action listener for different types of questions
	 */
	

	@Override
	public void onCancel() {
		try {
			DBController.getInstance().delete("Template","Template="+ DBController.appendApo(this.getTemplateName()));
			DBController.getInstance().delete("Survey_Template","Template="+ DBController.appendApo(this.getTemplateName()));
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		createSurvey.getSurveyTemplateListModel().getData();
		
	}
}

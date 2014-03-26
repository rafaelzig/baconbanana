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
	
	
	public TextualQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		initiWidgetsQt(QuestionType.TEXTUAL);
	}
}
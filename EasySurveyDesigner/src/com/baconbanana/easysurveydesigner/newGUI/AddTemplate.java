package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.DateQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.NumericQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.RatingQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.TextualQuestion;
import com.baconbanana.easysurveydesigner.newGUI.CreateSurvey;


public class AddTemplate extends Template{
		DBController dbCon;
	public AddTemplate(String tit, int width, int height) {
		super(tit, width, height);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(createQuestionBtn)){
			QuestionType type = (QuestionType) typeComboBox.getSelectedItem();
			String tit = new String("New " + type.toString());
			System.out.println(type);
			switch(type){
			case NUMERICAL :

				new NumericQuestion(tit, 800, 500, this);
				break;
			case DATE :
				new DateQuestion(tit, 800, 500, this);
				break;	
			case TEXTUAL :
				new TextualQuestion(tit, 800, 500, this);
				break;	
			case MULTIPLECHOICE :
				new MultipleChoiceQuestion(tit, 800, 500, this);
				break;
			case MULTIPLEANSWER :
				new MultipleAnswerQuestion(tit, 800, 500, this);
				break;
			case RATING :
				new RatingQuestion();
				break;
			case CONTINGENCY :
				new ContingencyQuestion();
				break;

			}
		}
		//		}else if(e.getSource().equals(addExistingQuestionBtn)){
		//			//TODO addExistingQuestionBtn
		//		}
		//		else if(e.getSource().equals(deleteBtn)){
		//			//TODO deleteBtn
		//		}
		else if(e.getSource().equals(getSaveBtn())){
			try {
			dbCon = DBController.getInstance();
			if (!(dbCon.exists("Template", "Template = " + DBController.appendApo(this.getTemplateName())))){
				JOptionPane.showMessageDialog(null, "Template is not saved because you have not added any questions to it.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getWindow().dispose();
		}else if(e.getSource().equals(getCancelBtn())){

			getWindow().dispose();

		}


	}
}

package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.event.ActionEvent;
import java.util.List;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.DateQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.NumericQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.RatingQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.TextualQuestion;

/**
 * class for Adding Templates
 * @author ZimS
 *
 */

public class AddTemplate extends Template{

	public AddTemplate(String tit, int width, int height, List<Object[]> tempList) {
		super(tit, width, height, tempList);
		
	}
	/**
	 * action listener for different types of questions
	 */
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
//		}else if(e.getSource().equals(addExistingQuestionBtn)){
//			//TODO addExistingQuestionBtn
//		}
//		else if(e.getSource().equals(deleteBtn)){
//			//TODO deleteBtn
//		}
//		else if(e.getSource().equals(saveBtn)){
//			
		}else if(e.getSource().equals(getCancelBtn())){
			getWindow().dispose();
		}
	}

	

}

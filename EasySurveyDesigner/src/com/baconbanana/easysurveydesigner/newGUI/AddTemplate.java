package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.util.List;

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


/**
 * class for Adding Templates
 * @author ZimS
 *
 */


public class AddTemplate extends Template{

		private DBController dbCon;
		private CreateSurvey createSurvey;
		
public AddTemplate(String tit, int width, int height, CreateSurvey cs) {
		super(tit, width, height);
		createSurvey = cs;
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
				new RatingQuestion(tit, 800, 500, this);
				break;
			case CONTINGENCY :
				new ContingencyQuestion();
				break;

			}
		}
//				}else if(e.getSource().equals(addExistingQuestionBtn)){
//					//TODO addExistingQuestionBtn
//				}
				else if(e.getSource().equals(getDeleteBtn())){
					try {System.out.print(DBController.getInstance().select("Question","Content="+
							DBController.appendApo(getTemplateList().getSelectedValue()),"QuestionID").get(0)[0]);
							int id =(int) DBController.getInstance().select("Question","Content="+
							DBController.appendApo(getTemplateList().getSelectedValue()),"QuestionID").get(0)[0];

							DBController.getInstance().delete("Template", "QuestionID="+id+" and Template="+DBController.appendApo(this.getTemplateName()));
							getListModel().getData();
							} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}
				}
		else if(e.getSource().equals(getSaveBtn())){
			try {
			dbCon = DBController.getInstance();
			if (!(dbCon.exists("Template", "Template = " + DBController.appendApo(this.getTemplateName())))){
				JOptionPane.showMessageDialog(null, "Template is not saved because you have not added any questions to it.", "Info", JOptionPane.INFORMATION_MESSAGE);
			}else {
				dbCon.insertInto("Survey_Template", DBController.appendApo(createSurvey.getSurveyName()), DBController.appendApo(this.getTemplateName()));
				createSurvey.getSurveyPrevModel().getData("Survey_Template", "Survey = " + DBController.appendApo(createSurvey.getSurveyName()), 1, "Survey", "Template");
				createSurvey.getSurveyTemplateListModel().getData();
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

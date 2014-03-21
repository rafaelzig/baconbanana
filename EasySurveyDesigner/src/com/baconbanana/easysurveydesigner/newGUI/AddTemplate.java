package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.Table;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.*;



public class AddTemplate extends Template{

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
	}

	

}

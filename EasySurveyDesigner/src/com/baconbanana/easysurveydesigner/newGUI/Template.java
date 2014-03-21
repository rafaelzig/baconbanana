package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.DateQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.NumericQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.RatingQuestion;
import com.baconbanana.easysurveydesigner.newGUI.QuestionTypes.TextualQuestion;

public class Template extends SQLWindow{
	
	private String stage;
	private JButton createQuestionBtn;
	private JButton addExistingQuestionBtn;
	private JButton deleteBtn;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JLabel nameOfTemplateTxf;
	private JComboBox<QuestionType> typeComboBox;

	private JList<String> templateList;
	private SQLList templateModel;
	
	private String templateName;


	public Template(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		initiLayout();
	}
	private void initiWidgets(){

		JPanel stage = new JPanel(new GridBagLayout());
		
		nameOfTemplateTxf = new JLabel();

		createQuestionBtn = new JButton("Create New");
		addExistingQuestionBtn = new JButton("Add Existing");
		deleteBtn = new JButton("Delete");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		
		typeComboBox = new JComboBox<QuestionType>(QuestionType.values());
		
		templateModel = new SQLList("Question", 1, "QuestionID", "Content", "Type");
		templateList = new JList<>(templateModel);

		JScrollPane templateListsp = new JScrollPane(templateList);
		
		
		stage.add(new JPanel(), LayoutController.summonCon(0, 0, 3, 1, 10, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		stage.add(new JPanel(), LayoutController.summonCon(0, 0, 1, 4, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		stage.add(new JPanel(), LayoutController.summonCon(2, 0));

		stage.add(templateListsp, LayoutController.summonCon(1, 2, 1, 1, 80, 60, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
		templateList.setBorder(getBorder());
				
		JPanel jpButtons = new JPanel(new FlowLayout());
		
		jpButtons.add(typeComboBox);
		
		jpButtons.add(createQuestionBtn);
		createQuestionBtn.addActionListener(this);
		jpButtons.add(addExistingQuestionBtn);
		addExistingQuestionBtn.addActionListener(this);
		jpButtons.add(deleteBtn);
		deleteBtn.addActionListener(this);
		jpButtons.add(saveBtn);
		saveBtn.addActionListener(this);
		jpButtons.add(cancelBtn);
		cancelBtn.addActionListener(this);
		
		stage.add(jpButtons, LayoutController.summonCon(1, 3, 1, 1, 80, 10));
		
		getWindow().add(stage);
		setFrameOptions();
		
		templateName = JOptionPane.showInputDialog(null, "Enter Template Name : ", "Name Template", 1);
		nameOfTemplateTxf.setText("<html><p style='text-align:center;font-size:large;'><strong><i>" + templateName + "</i></strong></p><html>");
		
		stage.add(nameOfTemplateTxf, LayoutController.summonCon(1, 1, 1, 1, 80, 20, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//TODO sort out dispose and visibles to retain template name
		if(e.getSource().equals(createQuestionBtn)){
			QuestionType type = (QuestionType) typeComboBox.getSelectedItem();
			String tit = new String("New " + type.toString());
			System.out.println(type);
			switch(type){
			case NUMERICAL :
				new NumericQuestion(tit, 800, 500);
				break;
			case DATE :
				new DateQuestion(tit, 800, 500);
				break;	
			case TEXTUAL :
				new TextualQuestion(tit, 800, 500);
				break;	
			case MULTIPLECHOICE :
				new MultipleChoiceQuestion(tit, 800, 500);
				break;
			case MULTIPLEANSWER :
				new MultipleAnswerQuestion(tit, 800, 500);
				break;
			case RATING :
				new RatingQuestion();
				break;
			case CONTINGENCY :
				new ContingencyQuestion();
				break;
			
			}
		}
		else if(e.getSource().equals(addExistingQuestionBtn)){
			//TODO addExistingQuestionBtn
		}
		else if(e.getSource().equals(deleteBtn)){
			//TODO deleteBtn
		}
		else if(e.getSource().equals(saveBtn)){
			
		}else if(e.getSource().equals(cancelBtn)){
			getWindow().dispose();
		}
		
	}
	@Override
	public void setList(ListSelectionEvent e) {
		
	}

}

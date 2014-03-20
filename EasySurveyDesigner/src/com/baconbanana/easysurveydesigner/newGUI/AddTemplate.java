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



public class AddTemplate extends SQLWindow{

	private String stage;
	private JButton createQuestionBtn;
	private JButton addExistingQuestionBtn;
	private JButton deleteBtn;
	private JButton saveBtn;
	private JButton cancelBtn;
	private static JTextField nameOfTemplateTxf;
	private JComboBox<QuestionType> typeComboBox;

	private static JList<String> templateList = new JList<String>();
	private final static DefaultListModel<String> templateListModel = new DefaultListModel<String>();


	public AddTemplate(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		setFrameOptions();
		initiLayout();
	}
	private void initiWidgets(){

		
		getWindow().setLayout(new BorderLayout());

		createQuestionBtn = new JButton("Create question");
		addExistingQuestionBtn = new JButton("Add existing question");
		deleteBtn = new JButton("Delete");
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		nameOfTemplateTxf = new JTextField("Type name for this template here");

		// ---------------------set combo box-----------------------------------
		//TODO Fix combobox!!!
		typeComboBox = new JComboBox<QuestionType>(QuestionType.values());
		/*int count = 0;
		for (QuestionType q : QuestionType.values()){
			typeComboBox.addItem(typeComboBox.);
			}*/

		// --------------------------------------------------------------------

		getWindow().add(nameOfTemplateTxf, BorderLayout.NORTH);
		getWindow().add(getTemplateList(), BorderLayout.CENTER);
		getTemplateList().setBorder(getBorder());
				
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
		
		getWindow().add(jpButtons, BorderLayout.SOUTH);
		
		SQLList templateModel = new SQLList("Template", new String[] {"Template"} , 0);

		populateList(templateList, templateModel);
		
		setFrameOptions();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(createQuestionBtn)){
			QuestionType type = (QuestionType) typeComboBox.getSelectedItem();
			String tit = new String("New " + type.toString());
			System.out.println(type);
			switch(type){
			case NUMERICAL :
				new NumericQuestion(tit, 800, 800);
				break;
			case DATE :
				new DateQuestion(tit, 800, 800);
				break;	
			case TEXTUAL :
				new TextualQuestion(tit, 800, 800);
				break;	
			case MULTIPLECHOICE :
				new MultipleChoiceQuestion(tit, 800, 800);
				break;
			case MULTIPLEANSWER :
				new MultipleAnswerQuestion(tit, 800, 800);
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
			//TODO saveBtn
		}else if(e.getSource().equals(cancelBtn)){
			new Menu("Menu", 300, 300);
			getWindow().dispose();
		}
		
	}
	public static DefaultListModel<String> getTemplatelistmodel() {
		return templateListModel;
	}
	public static JList<String> getTemplateList() {
		return templateList;
	}
	public static void setTemplateList(JList<String> templateList) {
		AddTemplate.templateList = templateList;
	}
	@Override
	public void setList(ListSelectionEvent e) {
		
	}

}

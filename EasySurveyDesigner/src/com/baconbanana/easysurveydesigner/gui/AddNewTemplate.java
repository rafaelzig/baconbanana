package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

public class AddNewTemplate {
	private String stage;
	private JFrame window;
	private JButton createQuestion;
	private JButton addExistingQuestion;
	private JButton delete;
	private JButton save;
	private JButton cancel;
	static JTextField nameOfTemplate;
	private JComboBox typeComboBox;

	public static JList<String> Template = new JList<String>();
	final static DefaultListModel<String> myModel2 = new DefaultListModel<String>();

	public AddNewTemplate(String stage) {
		this.stage = stage;
		setThings();
		setListeners();
		window.setLocationRelativeTo(null);

	}

	public void setThings() {
		
		window = new JFrame("Create new template. Stage:" + this.stage);
		window.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);

		createQuestion = new JButton("Create question");
		addExistingQuestion = new JButton("Add existing question");
		delete = new JButton("Delete");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		nameOfTemplate = new JTextField("Type name for this template here");

		// ---------------------set combo box-----------------------------------
		typeComboBox = new JComboBox(QuestionType.values());
		int count = 0;
		/*for (QuestionType q : QuestionType.values()){
			typeComboBox.addItem(typeComboBox.);
			}*/
		// --------------------------------------------------------------------

		window.add(nameOfTemplate, BorderLayout.NORTH);
		window.add(Template, BorderLayout.CENTER);
		Template.setBorder(border);
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.add(typeComboBox);
		jpButtons.add(createQuestion);
		jpButtons.add(addExistingQuestion);
		jpButtons.add(delete);
		jpButtons.add(save);
		jpButtons.add(cancel);

		window.add(jpButtons, BorderLayout.SOUTH);

		Template.setModel(myModel2);

		window.pack();
		window.setSize(800, 800);
		window.setVisible(true);

	}

	public void setListeners() {
		createQuestion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e1) {
				QuestionType type = (QuestionType) typeComboBox.getSelectedItem();
				System.out.println(type);
				switch(type){
				case MULTIPLE_CHOICE :
					new NewMultipleChoice(type);
					break;
				case MULTIPLE_ANSWER :
					new NewMultipleAnswer(type);
					break;
				case NUMERIC :
					new NewOpenEnded(type);
					break;
				case SCALAR :
					break;
				}
				
				window.dispose();
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				String sql = "Question where QuestionID=" + Template.getSelectedIndex();
				DBOperation.deleteRecord(sql);
				
				myModel2.remove(Template.getSelectedIndex());
				Template.setModel(myModel2);
			}
		});

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3) {
				/*
				try {
					DataTest.SaveTemplate(nameOfTemplate.getText());
					DataTest.FillListOfTemplates();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				EasySurveyFrame.myModel1.addElement(nameOfTemplate.getText());
				EasySurveyFrame.List1.setModel(EasySurveyFrame.myModel1);
				*/
				new CreateSurvey();
				window.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				new CreateSurvey();
				window.dispose();
			}
		});
	}
}

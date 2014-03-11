package com.baconbanana.easysurveydesigner.oldGui;

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

public class AddNewTemplate {
	private String stage;
	private JFrame window;
	private JButton createQuestion;
	private JButton addExistingQuestion;
	private JButton edit;
	private JButton delete;
	private JButton save;
	private JButton cancel;
	static JTextField nameOfTemplate;
	private JComboBox<String> type;

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
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		nameOfTemplate = new JTextField("Type name for this template here");

		// ---------------------set combo box-----------------------------------
		type = new JComboBox<String>();
		String[] typesOfAnswers = { "Open ended question", "Multiple choice question", "Multiple answer question"};
		int count = 0;
		for (int i = 0; i < 3; i++)
			type.addItem(typesOfAnswers[count++]);
		// --------------------------------------------------------------------

		window.add(nameOfTemplate, BorderLayout.NORTH);
		window.add(Template, BorderLayout.CENTER);
		Template.setBorder(border);
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.add(type);
		jpButtons.add(createQuestion);
		jpButtons.add(addExistingQuestion);
		jpButtons.add(edit);
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
				String h = type.getSelectedItem().toString();
				
				if (h == "Multiple choice question"){
					new NewMultipleChoice(h);
				}
				
				if (h== "Multiple answer question"){
					new NewMultipleAnswer(h);
				}
				if (h == "Open ended question"){
					new NewOpenEnded(h);
				}
				window.dispose();
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
				//new CreateSurvey();
				window.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				//new CreateSurvey();
				window.dispose();
			}
		});
	}
}

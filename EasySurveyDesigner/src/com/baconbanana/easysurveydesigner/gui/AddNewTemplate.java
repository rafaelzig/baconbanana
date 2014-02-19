package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddNewTemplate {
	private String stage;
	private JFrame window;
	private JButton createQuestion;
	private JButton addExistingQuestion;
	private JButton delete;
	private JButton save;
	private JTextField nameOfTemplate;
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

		createQuestion = new JButton("Create question");
		addExistingQuestion = new JButton("Add existing question");
		delete = new JButton("Delete");
		save = new JButton("Save");
		nameOfTemplate = new JTextField("Type name for this template here");

		// ---------------------set combo box-----------------------------------
		type = new JComboBox<String>();
		String[] typesOfAnswers = { "Text Area", "Radio Button", "Check Box" };
		int count = 0;
		for (int i = 0; i < 3; i++)
			type.addItem(typesOfAnswers[count++]);
		// --------------------------------------------------------------------

		window.add(nameOfTemplate, BorderLayout.NORTH);
		window.add(Template, BorderLayout.CENTER);

		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.add(type);
		jpButtons.add(createQuestion);
		jpButtons.add(addExistingQuestion);
		jpButtons.add(delete);
		jpButtons.add(save);

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
				System.out.println(h);
				new AddNewQuestion(h);

			}
		});

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3) {
				EasySurveyFrame.myModel1.addElement(nameOfTemplate.getText());
				EasySurveyFrame.List1.setModel(EasySurveyFrame.myModel1);
				window.dispose();
			}
		});
	}
}

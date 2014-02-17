package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Mateusz Kusaj, Beka M and team
 * 
 * This class creates GUI to build each separate template. It allows users to create, edit or remove 
 * questions and add them to the template.
 *
 */

public class AddNewTemplate 
{

	JFrame window = new JFrame("Create new template");
	JButton createQuestion = new JButton ("Create question");
	JButton addExistingQuestion = new JButton ("Add existing question");
	JButton delete = new JButton ("Delete");
	JButton save = new JButton ("Save");

	JTextField nameOfTemplate = new JTextField("");

	static JList<String> Template = new JList<String>();

	final static DefaultListModel<String> myModel2 = new DefaultListModel<String>();

	/**
	 * Constructor method.
	 */

	public AddNewTemplate()
	{
		initWidgets();
		window.setLocationRelativeTo(null);
	}

	/**
	 * Class that creates all widgets as well as layouts and panels that are used to build this GUI.
	 */

	public void initWidgets()
	{

		window.setLayout(new BorderLayout());

		window.add(nameOfTemplate, BorderLayout.NORTH);

		window.add(Template, BorderLayout.CENTER);

		JPanel jpButtons = new JPanel(new FlowLayout());
		window.add(jpButtons, BorderLayout.SOUTH);

		jpButtons.add(createQuestion);
		jpButtons.add(addExistingQuestion);
		jpButtons.add(delete);
		jpButtons.add(save);


		Template.setModel(myModel2);

		/**
		 * Action Listener that opens new frame where user can create each separate question.
		 */

		createQuestion.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e1)
			{
				new AddNewQuestion();

			}


		});

		/**
		 * Action Listener that updates the list in EasySurveyFrame after adding there new template that has been just created under the name
		 * that is entered by the user into the text field at the top of the window. 
		 */

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3)
			{
				EasySurveyFrame.myModel1.addElement(nameOfTemplate.getText());
				EasySurveyFrame.List1.setModel(EasySurveyFrame.myModel1);
				window.dispose();
			}
		});

		window.pack();
		window.setSize(800,800);
		window.setVisible(true);


	}
}


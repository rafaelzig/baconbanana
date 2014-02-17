package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Mateusz Kusaj, Beka M & team. 
 * 
 * This class represents GUI used to create the question. User will have an option to add different types 
 * of answers as well as remove and edit them.  
 *
 */

public class AddNewQuestion 
{
	private String[] typesOfAnswers = { "Text Field", "Radio Button", "Check Box"};


	JFrame window = new JFrame("Add new question");

	JTextArea question = new JTextArea("");

	JTextField title = new JTextField("");

	JButton add = new JButton("Add");
	JButton remove = new JButton("Remove");
	JButton save = new JButton("Save");

	JCheckBox answerCheckBox;


	JComboBox<String> answer = new JComboBox<String>();
	int comboBox = 1;

	private int count = 0;
	int answerLimit = 0;

	/**
	 * Constructor method.
	 */

	public AddNewQuestion()
	{
		initWidgets();
	}

	/**
	 * Class that creates all widgets as well as layouts and panels that are used to build this GUI.
	 */

	public void initWidgets()
	{
		window.setLayout(new BorderLayout());

		JPanel jpQuestion = new JPanel(new BorderLayout());
		window.add(jpQuestion, BorderLayout.CENTER);
		window.add(title, BorderLayout.NORTH);
		jpQuestion.add(question, BorderLayout.CENTER);
		JPanel jpChoices= new JPanel(new BorderLayout());
		jpQuestion.add(jpChoices, BorderLayout.SOUTH);
		final JPanel jpRadioButtons = new JPanel(new GridLayout(20,1));
		final JPanel jpTextField = new JPanel(new GridLayout(20,1));
		final JPanel jpRemove = new JPanel(new GridLayout(20,1)); 
		jpChoices.add(jpRadioButtons, BorderLayout.WEST);
		jpChoices.add(jpTextField, BorderLayout.CENTER);
		jpChoices.add(jpRemove, BorderLayout.EAST);

		JPanel jpButtons = new JPanel(new FlowLayout());
		window.add(jpButtons, BorderLayout.SOUTH);
		jpButtons.add(answer);
		jpButtons.add(add);
		jpButtons.add(remove);
		jpButtons.add(save);

		for (int i = 0; i <3; i++)
			answer.addItem(typesOfAnswers[count++]);

		/**
		 * Action Listener that adds new answer text box. In addition to text box user 
		 * can add radio button or check box. 
		 */

		add.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e1)
			{
				if (answerLimit < 20)
				{
					if (answer.getSelectedIndex() == 0) 
					{

						jpRadioButtons.add(new JLabel(""));
						jpTextField.add(new JTextField(""));

						jpRemove.add(new JCheckBox());
						window.pack();
						window.setSize(800,800);
					}
					if (answer.getSelectedIndex() == 1) 
					{
						jpRadioButtons.add(new JRadioButton());
						jpTextField.add(new JTextField(""));
						jpRemove.add(new JCheckBox());
						window.pack();
						window.setSize(800,800);
					}
					if (answer.getSelectedIndex() == 2)
					{
						jpRadioButtons.add(new JCheckBox());
						jpTextField.add(new JTextField(""));
						jpRemove.add(new JCheckBox());
						window.pack();
						window.setSize(800,800);
					}
					answerLimit++;
				}
			}


		});

		/**
		 * Action Listener that allows user to delete answers.
		 */

		remove.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e2)
			{
				jpRadioButtons.remove(0);
				jpTextField.remove(0);
				jpRemove.remove(0);
				window.pack();
				window.setSize(800,800);

				answerLimit--;

			}
		});
		/**
		 * Action Listener that saves the question under the name that user entered at the top of the window.
		 * It also closes the form.
		 */

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3)
			{
				AddNewTemplate.myModel2.addElement(title.getText());
				AddNewTemplate.Template.setModel(AddNewTemplate.myModel2);
				window.dispose();
			}
		});

		window.pack();
		window.setSize(800,800);
		window.setVisible(true);
	}

}

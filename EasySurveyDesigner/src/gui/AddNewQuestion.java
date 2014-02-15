package gui;


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

public class AddNewQuestion 
{
	private String[] typesOfAnswers = { "Text Field", "Radio Button", "Check Box"};

	JFrame window = new JFrame("Add new question");

	JTextArea question = new JTextArea("");
	JTextField choices = new JTextField("dsgxdzsrgdxfg");
	JButton add = new JButton("Add");
	JButton remove = new JButton("Remove");
	
	JCheckBox answerCheckBox;
	

	JComboBox<String> answer = new JComboBox<String>();
	int comboBox = 1;

	private int count = 0;
	int answerLimit = 0;
	

	public AddNewQuestion()
	{
		initWidgets();
	}

	public void initWidgets()
	{
		window.setLayout(new BorderLayout());

		JPanel jpQuestion = new JPanel(new BorderLayout());
		window.add(jpQuestion, BorderLayout.CENTER);
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

		for (int i = 0; i <3; i++)
			answer.addItem(typesOfAnswers[count++]);

		add.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e1)
			{
				if (answerLimit < 20)
				{
					if (answer.getSelectedIndex() == 0) 
					{
						//answerTextField = new JTextField();
						//answerTextField.setName(answerText + answerLimit);
						//jpTextField.add(answerTextField);
						
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
		
		window.pack();
		window.setSize(800,800);
		window.setVisible(true);
	}

	protected void AddNewAnswer() {


	}

}

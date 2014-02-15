package gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	
	JComboBox<String> answer = new JComboBox<String>();
	
	private int count = 0;
	
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
		final JPanel jpChoices= new JPanel(new GridLayout(10,1));
		jpQuestion.add(jpChoices, BorderLayout.SOUTH);
		
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
					
					jpChoices.add(new JTextField("gfsfs"));	
		            window.pack();
		            window.setSize(800,800);	
				}

				
			
					
					});
		
		window.pack();
		window.setSize(800,800);
		window.setVisible(true);
	}

	protected void AddNewAnswer() {
		
		
	}
	
}

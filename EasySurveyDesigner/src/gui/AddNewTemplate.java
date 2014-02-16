package gui;

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
	
	public AddNewTemplate()
	{
		initWidgets();
	}
	
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
		
		createQuestion.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e1)
			{
				new AddNewQuestion();
				
			}
		
				
				});
		
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


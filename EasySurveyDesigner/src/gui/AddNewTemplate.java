package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class AddNewTemplate 
{

	JFrame window = new JFrame("Create new template");
	JButton CreateQuestion = new JButton ("Create question");
	JButton AddExistingQuestion = new JButton ("Add existing question");
	JButton Delete = new JButton ("Delete");
	JButton Save = new JButton ("Save");
	
	JList<Object> Template = new JList<Object>();
	
	public AddNewTemplate()
	{
		initWidgets();
	}
	
	public void initWidgets()
	{
	
		window.setLayout(new BorderLayout());
		
		window.add(Template, BorderLayout.CENTER);
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		window.add(jpButtons, BorderLayout.SOUTH);
		
		jpButtons.add(CreateQuestion);
		jpButtons.add(AddExistingQuestion);
		jpButtons.add(Delete);
		jpButtons.add(Save);
		
		window.pack();
		window.setSize(800,800);
		window.setVisible(true);
		
		
	}
}


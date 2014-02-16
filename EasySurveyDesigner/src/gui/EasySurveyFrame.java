package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

public class EasySurveyFrame 
{

	JFrame window = new JFrame("Easy Survey");
	
	JLabel Information1 = new JLabel("Information 1");
	JLabel Information2 = new JLabel("Information 2");
	JLabel Information3 = new JLabel("Information 3");
	JLabel Filler = new JLabel(" ");
	
	static JList<String> List1 = new JList<String>();
	JList<String> List2 = new JList<String>();
	JList<String> List3 = new JList<String>();
	
	
	
	JButton Add = new JButton("Add");
	JButton Save = new JButton("Save");
	JButton Delete = new JButton("Delete");
	JButton Send = new JButton("Send");
	
	BasicArrowButton Move = new BasicArrowButton(BasicArrowButton.SOUTH);
	
	final static DefaultListModel<String> myModel1 = new DefaultListModel<String>();
	

	public EasySurveyFrame()
	{
		initWidgets();
	}
	
	public void initWidgets()
	{
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setLayout(new BorderLayout());
		
		JPanel jpTemplates = new JPanel(new BorderLayout());
		window.add (jpTemplates, BorderLayout.WEST);
		
		jpTemplates.add(Information1, BorderLayout.NORTH);
		jpTemplates.add(List1, BorderLayout.CENTER);
		JPanel jpTemplatesButtons = new JPanel(new FlowLayout());
		jpTemplates.add(jpTemplatesButtons, BorderLayout.SOUTH);
		jpTemplatesButtons.add(Add);
		jpTemplatesButtons.add(Save);
		jpTemplatesButtons.add(Delete);
		jpTemplatesButtons.add(Move);
		
		JPanel jpTemplatesPreview = new JPanel(new BorderLayout());
		window.add (jpTemplatesPreview, BorderLayout.CENTER);
		
		jpTemplatesPreview.add(Information2, BorderLayout.NORTH);
		jpTemplatesPreview.add(List2, BorderLayout.CENTER);
		
		JPanel jpQuestionsPreview = new JPanel(new BorderLayout());
		window.add (jpQuestionsPreview, BorderLayout.SOUTH);
		
		jpQuestionsPreview.add(Information3, BorderLayout.NORTH);
		jpQuestionsPreview.add(List3, BorderLayout.CENTER);
		JPanel jpQuestionsButton = new JPanel(new FlowLayout());
		jpQuestionsPreview.add(jpQuestionsButton, BorderLayout.SOUTH);
		jpQuestionsButton.add(Filler);
		jpQuestionsButton.add(Filler);
		jpQuestionsButton.add(Send);
		
		
		Add.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e1)
			{
				AddNewTemplate.myModel2.clear();
				new AddNewTemplate();
				
			}
		
				
				});
		
		
		
		
		
		window.pack();
		window.setSize(800,800);
		window.setVisible(true);
	}
	
	
	public static void main(String[] args) 
	{
		new EasySurveyFrame();
	}
}

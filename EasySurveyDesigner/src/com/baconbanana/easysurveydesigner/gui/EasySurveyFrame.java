package com.baconbanana.easysurveydesigner.gui;

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

/**
 * 
 * @author Mateusz Kusaj & team 
 * 
 * This class represents GUI for the fist and most important frame. In this frame user will be able to merge 
 * different templates into one questioner and also preview, edit or delete those templates. 
 *
 */

public class EasySurveyFrame 
{

	JFrame window = new JFrame("Easy Survey");

	JLabel Information1 = new JLabel("List of templates");
	JLabel Information2 = new JLabel("Template preview");
	JLabel Information3 = new JLabel("Questionare preview");
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

	/**
	 * Constructor method.
	 */

	public EasySurveyFrame()
	{
		initWidgets();
	}

	/**
	 * Method responsible for initialising the widgets as well as layouts and panels used to create this GUI.
	 */

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

		/**
		 * Action Listener that send user to another window where he/she can design their 
		 * template. 
		 */

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

	/**
	 * 
	 * @param args
	 * 
	 * Main method that initialise this class. 
	 * 
	 */

	public static void main(String[] args) 
	{
		new EasySurveyFrame();
	}
}

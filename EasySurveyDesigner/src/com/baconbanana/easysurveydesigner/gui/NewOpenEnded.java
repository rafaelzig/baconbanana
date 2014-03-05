package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class NewOpenEnded {

	String questionType;
	JFrame window;
	JTextArea question;
	JTextField title;
	JButton save;
	JTextArea answer;
	JButton cancel;

	public NewOpenEnded(String type) {
		this.questionType = type;
		window = new JFrame("New " + questionType);
		setThings();
		
	}

	public void setThings() {

		// ---------------------------------------------------

		window.setLayout(new BorderLayout());
		
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);

		// --------------center of window---------------------
		question = new JTextArea("Type your question here");
		question.setPreferredSize(new Dimension(800, 280));
		question.setBorder(border);
		window.add(question, BorderLayout.CENTER);
		// ---------------------------------------------------

		// --------------North of window----------------------
		title = new JTextField("Type your title here");
		title.setPreferredSize(new Dimension(800, 20));
		window.add(title, BorderLayout.NORTH);
		// ---------------------------------------------------

		// --------------South of window----------------------
		

		JPanel panelSouth = new JPanel(new BorderLayout());
		answer = new JTextArea("Type your answer here");
		answer.setPreferredSize(new Dimension(800, 200));
		answer.setBorder(border);
		
		panelSouth.add(answer, BorderLayout.NORTH);
		
		
		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));

		save = new JButton("save");
		jpButtons.add(save);
		cancel = new JButton("Cancel");
		jpButtons.add(cancel);

		panelSouth.add(jpButtons, BorderLayout.SOUTH);

		window.add(panelSouth, BorderLayout.SOUTH);

		window.pack();
		window.setSize(800, 800);
		window.setVisible(true);
		// ---------------------------------------------------

		window.setLocationRelativeTo(null);
	

	
		

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3) {
				AddNewTemplate.myModel2.addElement(title.getText() + "("
						+ questionType + ")");
				AddNewTemplate.Template.setModel(AddNewTemplate.myModel2);
				new AddNewTemplate("test");
				window.dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() 
		
		{
			public void actionPerformed(ActionEvent e) {
				new AddNewTemplate("test");
				window.dispose();
				
			}
		});

	}

}

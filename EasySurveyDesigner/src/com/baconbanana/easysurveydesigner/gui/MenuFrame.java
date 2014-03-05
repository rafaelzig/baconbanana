package com.baconbanana.easysurveydesigner.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuFrame {
	JFrame window = new JFrame ("Easy Survey");
	JButton createSurvey = new JButton ("Create new Survey");
	JButton openSurvey = new JButton ("Open Survey"); 
	JButton previewAnswers = new JButton ("<html>Previews answers made <br> by the patients</html>");
	
	
	public MenuFrame ()
	{
		initWidgets();
		window.setLocationRelativeTo(null);
	}

	private void initWidgets() {
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(5,3));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(createSurvey);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(openSurvey);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(previewAnswers);
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		window.add(new JLabel (" "));
		
		createSurvey.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e1) {
				new CreateSurvey();
				window.dispose();
				
			}
		});
		
		previewAnswers.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e2) {
				new ListOfPatients();
				window.dispose();
				
			}
		});
		
		window.pack();
		window.setSize(600, 600);
		window.setVisible(true);
	}
}

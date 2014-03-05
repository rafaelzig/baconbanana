package com.baconbanana.easysurveydesigner.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ListOfPatients {
	
	JFrame window = new JFrame ("List of patients");
	JList<String> ListOfPatients = new JList<String>();
	JButton ShowSurveys = new JButton("Show Surveys");
	
	public ListOfPatients() {
		
		initWidgets();
		window.setLocationRelativeTo(null);
		
	}

	private void initWidgets() {
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		window.add(ListOfPatients, BorderLayout.CENTER);
		JPanel jpButtons = new JPanel(new FlowLayout());
		window.add(jpButtons, BorderLayout.SOUTH);
		jpButtons.add(new JLabel(" "));
		jpButtons.add(ShowSurveys);
		jpButtons.add(new JLabel(" "));
		
		ShowSurveys.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e1) {

			}
		});
		
		window.pack();
		window.setSize(800, 800);
		window.setVisible(true);
		
	}
}

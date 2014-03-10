package com.baconbanana.easysurveydesigner.oldGui;

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

public class NewMultipleChoice {

	String questionType;
	JFrame window;
	JTextArea question;
	JButton add;
	JButton remove;
	JButton save;
	JButton cancel;
	int answerLimit = 0;
	DefaultTableModel model;
	JTable table;

	public NewMultipleChoice(String type) {
		this.questionType = type;
		window = new JFrame("New " + questionType + " question");
		setThings();
		setListeners();
	}

	public void setThings() {

		// ---------------------------------------------------

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);

		// --------------center of window---------------------
		question = new JTextArea("Type your question here");
		question.setPreferredSize(new Dimension(800, 280));
		question.setBorder(border);
		window.add(question, BorderLayout.CENTER);
		// ---------------------------------------------------

		
		// --------------South of window----------------------
		Object[] columnNames = { "Answer", "Select" };
		Object[][] data = {};
		model = new DefaultTableModel(data, columnNames);
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				default:
					return Boolean.class;
				}
			}
		};
		model.addRow(new Object[] { "type answer here", false });
		model.addRow(new Object[] { "type answer here", false });

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(200, 400));

		JPanel panelSouth = new JPanel(new BorderLayout());
		panelSouth.add(scrollPane, BorderLayout.NORTH);

		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));

		add = new JButton("Add");
		jpButtons.add(add);
		remove = new JButton("Remove");
		jpButtons.add(remove);
		save = new JButton("Save");
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
	}

	public void setListeners() {
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e1) {
				if (answerLimit < 10) {
					
					model.addRow(new Object[] { "type answer here", false });
					answerLimit++;
				}
			}

		});
		remove.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e2) {
				for (int x = 0; x < model.getRowCount(); x++) {

					if ((boolean) model.getValueAt(x, 1) == true) {
						model.removeRow(x);
					}

				}

				window.pack();
				window.setSize(800, 800);

				answerLimit--;

			}
		});

		save.addActionListener(new ActionListener()

		{
			public void actionPerformed(ActionEvent e3) {
				AddNewTemplate.myModel2.addElement(question.getText() + "("
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

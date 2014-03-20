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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.old.DBOperationOld;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

public class NewMultipleChoice {

	QuestionType questionType;
	JFrame window;
	JTextArea question;
	JButton add;
	JButton remove;
	JButton save;
	JButton cancel;
	int answerLimit = 0;
	DefaultTableModel model;
	JTable table;

	public NewMultipleChoice(QuestionType type) {
		this.questionType = type;
		window = new JFrame("New " + questionType.toString() + " Question");
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
	//TODO edit this class eventally get to this bit
	/*private void saveQuestion(){
		//Add question to question table
		String sql = "INSERT INTO Question (Content, Type) VALUES ('" + question.getText() + "', " + questionType + "')";
		System.out.println(sql);
		DBOperation.insertRecord(sql);
		//Add choices to choices table
		String[] choices = new String[9];
		for(int i = 0;i < answerLimit; i++){
			choices[i] = ((String) model.getValueAt(0, i));
			sql = "INSERT INTO Choices (Choice) Values ('" + choices[i] + "')";
			int lastId = DBOperation.insertRecordReturnID(sql);
			System.out.println(lastId);
		}
		
	}*/

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

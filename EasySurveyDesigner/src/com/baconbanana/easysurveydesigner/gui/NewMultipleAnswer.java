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

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBOperation;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

public class NewMultipleAnswer {

	private QuestionType questionType;
	private JFrame window;
	private JTextArea question;
	private JButton add;
	private JButton remove;
	private JButton save;
	private JButton cancel;
	private int answerLimit = 0;
	private DefaultTableModel model;
	private JTable table;
	private String[] options = new String[9];
	
	//instaiate window and set title
	public NewMultipleAnswer(QuestionType type) {
		this.questionType = type;
		window = new JFrame("New " + questionType.toString() + " Question");
		//should be setLayout
		setThings();
		setListeners();
	}

	public void setThings() {

		// ---------------------------------------------------

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		//Question name and data
		// --------------center of window---------------------
		question = new JTextArea("Type your question here");
		question.setPreferredSize(new Dimension(800, 280));
		question.setBorder(border);
		window.add(question, BorderLayout.CENTER);
		// ---------------------------------------------------

		//not sure why we need checkboxes
		// --------------South of window----------------------
		String[] columnNames = { "Answer", "Select" };
		model = new DefaultTableModel(new String[1][9], columnNames);
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;
			//think this is only needed for sorting
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
		//instatiate checkboxes
		options[answerLimit++] = "Type Option Here";
		options[answerLimit++] = "Type Option Here";
		this.reDraw(model, options);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(200, 400));

		JPanel panelSouth = new JPanel(new BorderLayout());
		panelSouth.add(scrollPane, BorderLayout.NORTH);

		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));
		//instatiate buttons
		add = new JButton("add");
		jpButtons.add(add);
		remove = new JButton("remove");
		jpButtons.add(remove);
		save = new JButton("save");
		jpButtons.add(save);
		cancel = new JButton("Cancel");
		jpButtons.add(cancel);
		//add buttons
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
				
				String sql = "SELECT * FROM Type WHERE Type = 'Multiple Answer'";
				if (!DBOperation.existsRecord(sql)) {
					String sql1 = "Type VALUES ('Multiple Answer')";
					DBOperation.insertRecord(sql1);
				}
				
				String sql2 = "Question VALUES (NULL, '" + question.getText() + "', 'Multiple Answer')";
				DBOperation.insertRecord(sql2);
				
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
	//this methord should ideally be in the model
	/*
	private void saveQuestion(){
		//Add question to question table
		String sql = "INSERT INTO Question (Content, Type) VALUES ('" + question.getText() + "', " + questionType + "')";
		System.out.println(sql);
		DBOperation.insertRecord(sql);
		//Add choices to choices table
		String[] choices = new String[9];
		for(int i = 0;i < answerLimit; i++){
			choices[i] = ((String) model.getValueAt(0, i));
			sql = "INSERT INTO Choices (Choice) Values ('" + choices[i] + "')";
			int lastId;
			if((lastId = DBOperation.insertRecordReturnID(sql)) > 0){
				System.out.println(lastId);
			}
			System.out.println(lastId);
		}
		
	}
	*/
	
	private void reDraw(DefaultTableModel m, String[] data){
		for(int i = 0; i < answerLimit; i++){
			model.addRow(new Object[] { data[i], false });
		}
	}

}

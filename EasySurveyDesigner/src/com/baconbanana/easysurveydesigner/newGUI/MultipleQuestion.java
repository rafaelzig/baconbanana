package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class MultipleQuestion extends Question{

	private JButton addBtn;
	private JButton removeBtn;
	private int answerLimit = 0;
	private DefaultTableModel choicesTableModel;
	private JTable choicesTable;
	private String[] options = new String[9];

	public MultipleQuestion(String tit, int width, int height) {
		super(tit, width, height);
		initiWidgets();
		initiWidgetsMq();
		setFrameOptions();
		initiLayout();
	}

	public void initiWidgetsMq(){
		// --------------center of window---------------------
		setQuestionTxa(new JTextArea("Type your question here"));
		getQuestionTxa().setPreferredSize(new Dimension(800, 280));
		getQuestionTxa().setBorder(getBorder());
		getWindow().add(getQuestionTxa(), BorderLayout.CENTER);
		// ---------------------------------------------------

		//not sure why we need checkboxes
		// --------------South of window----------------------
		String[] columnNames = { "Answer", "Select" };
		choicesTableModel = new DefaultTableModel(new String[1][9], columnNames);
		choicesTable = new JTable(choicesTableModel) {
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
		//I have broken reDraw thing :( but I only renamed the model :(
		//getWindow().reDraw(choicesTableModel, options);
		JScrollPane scrollPane = new JScrollPane(choicesTable);
		scrollPane.setPreferredSize(new Dimension(200, 400));

		JPanel panelSouth = new JPanel(new BorderLayout());
		panelSouth.add(scrollPane, BorderLayout.NORTH);

		JPanel jpButtons = new JPanel(new FlowLayout());
		jpButtons.setPreferredSize(new Dimension(800, 50));

		//instatiate buttons
		addBtn = new JButton("add");
		jpButtons.add(addBtn);
		addBtn.addActionListener(this);
		removeBtn = new JButton("remove");
		jpButtons.add(removeBtn);
		removeBtn.addActionListener(this);
		setSaveBtn(new JButton("save"));
		jpButtons.add(getSaveBtn());
		getSaveBtn().addActionListener(this);
		setCancelBtn(new JButton("Cancel"));
		jpButtons.add(getCancelBtn());
		getCancelBtn().addActionListener(this);
		//add buttons
		panelSouth.add(jpButtons, BorderLayout.SOUTH);

		getWindow().add(panelSouth, BorderLayout.SOUTH);
	}
	
	public void saveQuestionMa(String questionAnswerText){
		DBController controller = null;
		String tableName = new String("Choices");
		ArrayList<String> values = new ArrayList<String>();
		values.add("null");
		values.add("'" + questionAnswerText + "'");
		try {
			try {
				controller = DBController.getInstance();
				controller.loadResources();
				controller.insertInto(tableName, values);
			}catch (InvalidStateException e1) {
				e1.printStackTrace();
			}finally{
				if (controller != null)
					controller.close();
			}
		}catch (SQLException | ClassNotFoundException e2){
		
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
			System.exit(-1);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			saveQuestion();
		}
		else if(e.getSource().equals(getCancelBtn())){
			cancelQuestion();
		}
		else if(e.getSource().equals(addBtn)){
			if (answerLimit < 10) {
				choicesTableModel.addRow(new Object[] { "type answer here", false });
				answerLimit++;
			}
		}
		else if(e.getSource().equals(removeBtn)){
            //TODO fix it
			for (int x = 0; x < choicesTableModel.getRowCount(); x++) {

				if ((boolean) choicesTableModel.getValueAt(x, 1) == true) {
					choicesTableModel.removeRow(x);
				}

			}
		}
	}


}

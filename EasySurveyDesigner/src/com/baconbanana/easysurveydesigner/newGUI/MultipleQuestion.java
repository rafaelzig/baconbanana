package com.baconbanana.easysurveydesigner.newGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import com.baconbanana.easysurveydesigner.functionalCore.LayoutController;
import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * class for creating different multiple answer questions
 * @author ZimS
 *
 */
public class MultipleQuestion extends Question{

	
	private JButton addBtn;
	private JButton removeBtn;
	private DefaultTableModel choicesTableModel;
	private JTextPane questionTxta;
	private JList<String> choicesList;
	private SQLList choiceModel;
	private String[] options = new String[9];
	private Template template;
	private int questionId = 0;
	private DBController dbCon;
	private String questText;
	

	public MultipleQuestion(String tit, int width, int height, Template t) {
		super(tit, width, height, t);
		template = t;
	}

	public void initiWidgetsQt(QuestionType qt){
		setQuestionType(qt);
		JPanel panel = new JPanel(new GridBagLayout());
		getWindow().add(panel);
		questionTxta = new JTextPane();
		panel.add(questionTxta, LayoutController.summonCon(1, 1, 1, 1, 8, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		choiceModel = new SQLList("Choice NATURAL JOIN Question_Choice", "QuestionID=" + questionId, 0, "Choice");
		choicesList = new JList<String>(choiceModel);
		JScrollPane choicesListsp = new JScrollPane(choicesList);
		panel.add(choicesListsp, LayoutController.summonCon(1, 2));
//		// --------------center of window---------------------
//		setQuestionTxa(new JTextPane());
//		getQuestionTxa().setPreferredSize(new Dimension(800, 280));
//		getQuestionTxa().setBorder(getBorder());
//		getWindow().add(getQuestionTxa(), BorderLayout.CENTER);
//		// ---------------------------------------------------
//
//		//not sure why we need checkboxes
//		// --------------South of window----------------------
//		String[] columnNames = { "Answer", "Select" };
//		choicesTableModel = new DefaultTableModel(new String[1][9], columnNames);
//		choicesTable = new JTable(choicesTableModel) {
//			private static final long serialVersionUID = 1L;
//			//think this is only needed for sorting
//			@Override
//			public Class getColumnClass(int column) {
//				switch (column) {
//				case 0:
//					return String.class;
//				default:
//					return Boolean.class;
//				}
//			}
//		};
//		//instatiate checkboxes
//		options[answerLimit++] = "Type Option Here";
//		options[answerLimit++] = "Type Option Here";
//		//I have broken reDraw thing :( but I only renamed the model :(
//		//getWindow().reDraw(choicesTableModel, options);
//		JScrollPane scrollPane = new JScrollPane(choicesTable);
//		scrollPane.setPreferredSize(new Dimension(200, 400));
//
//		JPanel panelSouth = new JPanel(new BorderLayout());
//		panelSouth.add(scrollPane, BorderLayout.NORTH);
//
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
//		//add buttons
		panel.add(jpButtons, LayoutController.summonCon(1, 3, 1, 1, 80, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH));
//
//		getWindow().add(panelSouth, BorderLayout.SOUTH);
	}
	/**
	 * adds question choices into database
	 * @param choices
	 */
	
/**
 * button listener
 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(getSaveBtn())){
			template.getListModel().getData();
			getWindow().dispose();
		}
		else if(e.getSource().equals(getCancelBtn())){
			cancelQuestion();
		}
		else if(e.getSource().equals(addBtn)){
			//check if user has entered data into question box
			if(!questionTxta.getText().equals("")){
				questText = questionTxta.getText();
				//if question has not been created yet
				if(questionId == 0){
					try{
						dbCon = DBController.getInstance();
						//add question and add that question to tempalte
						questionId = dbCon.insertInto("Question", "null", DBController.appendApo(questText), DBController.appendApo(getQuestionType().toString()));
						dbCon.insertInto("Template", DBController.appendApo(template.getTemplateName()), String.valueOf(questionId));
					}catch(SQLException | ClassNotFoundException ee){
						ee.printStackTrace();
					}
				}
				String choice = null;
				//loop until choice has been filled out
				while(choice == null){
					//get choice
				choice = JOptionPane.showInputDialog(null, "Enter Numeric Question : ", "New Numeric Question", 1);
					if(choice != null){
						//create new model for choice window
						choiceModel = new SQLList("Choice NATURAL JOIN Question_Choice", "QuestionID=" + questionId, 0, "Choice");
						populateList(choicesList, choiceModel);
						//insert choice into model
						int choiceId = choiceModel.insertElement("Choice", "null", DBController.appendApo(choice));
						try{
							dbCon = DBController.getInstance();
							//insert question and choice linkup
							dbCon.insertInto("Question_Choice", String.valueOf(questionId), String.valueOf(choiceId));
						}catch(SQLException | ClassNotFoundException ee){
							ee.printStackTrace();
						}
					}
					//reload choices
					choiceModel.getData();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Please Fill Out The Question First", "Fill Out Question", JOptionPane.INFORMATION_MESSAGE);
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
	/**
	 * 
	 * @return array of choices
	 */
	public String[] getChoicesTable(){
		String[] cho = new String[choicesTableModel.getRowCount()];
		for(int i = 0; i < choicesTableModel.getRowCount();i++){
			System.out.println(choicesTableModel.getValueAt(i, 0));
			cho[i] = (String) choicesTableModel.getValueAt(i, 0).toString();
		}
		return cho;
	}
}




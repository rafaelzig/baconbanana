package com.baconbanana.easysurveydesigner.GUI;

import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * parsing queries for database
 * @author ZimS
 *
 */
public abstract class SQLWindow extends Window implements ListSelectionListener{
	
	private DBController dbCon;
	private String context;
	
	public SQLWindow(String tit, boolean fullScreen) {
		super(tit, fullScreen);

	}
	public SQLWindow(String tit, int width, int height) {
		super(tit, width, height);

	}
	public void populateList(JList<String> list, SQLList listModel){
		list.setModel(listModel);
	}
	public abstract void valueChanged(ListSelectionEvent e);

	 
	public void createContext(String tableName, String...values){
		try {
			dbCon = DBController.getInstance();
			//may have an issue with ambiguity
			dbCon.insertInto(tableName, DBController.appendApo(values));
			context = values[0];
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getContext(){
		return context;
	}
}

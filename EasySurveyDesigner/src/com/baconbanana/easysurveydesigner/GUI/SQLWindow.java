package com.baconbanana.easysurveydesigner.GUI;

import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;
/**
 * A class for managing windows that use SQL
 *
 */
public abstract class SQLWindow extends Window implements ListSelectionListener{
	
	private DBController dbCon;
	private String context;
	/**
	 * Constructor for full screen windows
	 * @param tit title 
	 * @param fullScreen if true window will be full screen
	 */
	public SQLWindow(String tit, boolean fullScreen) {
		super(tit, fullScreen);

	}
	/**
	 * Constructor for small window
	 * @param tit title
	 * @param width
	 * @param height
	 */
	public SQLWindow(String tit, int width, int height) {
		super(tit, width, height);

	}
	/**
	 * get model for the list
	 * @param list
	 * @param listModel
	 */
	public void populateList(JList<String> list, SQLList listModel){
		list.setModel(listModel);
	}
	public abstract void valueChanged(ListSelectionEvent e);

	 /**
	  * Create a record in the database for current window
	  * @param tableName
	  * @param values
	  */
	public void createContext(String tableName, String...values){
		try {
			dbCon = DBController.getInstance();
			dbCon.insertInto(tableName, DBController.appendApo(values));
			context = values[0];
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public String getContext(){
		return context;
	}
}

package com.baconbanana.easysurveydesigner.newGUI;

import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public abstract class SQLWindow extends Window implements ListSelectionListener{
	
	private DBController dbCon;
	
	public SQLWindow(String tit, boolean fullScreen) {
		super(tit, fullScreen);
	}
	public SQLWindow(String tit, int width, int height) {
		super(tit, width, height);
	}
	private void getCon(){
		try {
			dbCon = DBController.getInstance();
			dbCon.loadResources();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void populateList(JList<String> list, SQLList listModel){
		list.setModel(listModel);
	}
	 public void valueChanged(ListSelectionEvent e) {
		 ListSelectionModel lsm = (ListSelectionModel)e.getSource();
		 
		 
	 }
	 public abstract void setList(ListSelectionEvent e);
}

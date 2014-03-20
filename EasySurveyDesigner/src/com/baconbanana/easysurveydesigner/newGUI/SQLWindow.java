package com.baconbanana.easysurveydesigner.newGUI;

import javax.swing.JList;

import com.baconbanana.easysurveydesigner.functionalCore.models.SQLList;

public abstract class SQLWindow extends Window{
	
	public SQLWindow(String tit, boolean fullScreen) {
		super(tit, fullScreen);
	}
	public SQLWindow(String tit, int width, int height) {
		super(tit, width, height);
	}
	
	public void populateList(JList<String> list, SQLList listModel){
		list.setModel(listModel);
	}

}

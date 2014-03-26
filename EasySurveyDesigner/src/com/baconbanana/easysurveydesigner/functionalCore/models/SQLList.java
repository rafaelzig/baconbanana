package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;

public class SQLList extends AbstractListModel{
	
	private List<Object[]> data;
	private String table;
	private String[] columns;
	private int sortColumn;
	private DBController dbCon;
	private ResultSet rs;
	private String condition;
	
	
	//get ID at some point
	public SQLList(String tableName, int sortCol, String... col){
		super();
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		sortColumn = sortCol;
	}
	
	public SQLList(String tableName, String condition, int sortCol, String...col){
		super();
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		this.condition = condition;
		sortColumn = sortCol;
	}
	
	@Override
	public String getElementAt(int i) {
		return (String) data.get(i)[sortColumn];
	}
	/**
	 * 
	 * @param i index of value
	 * @return ID or 0 if invalid
	 */
	public String getId(int i){
		if(i >= 0){
			return  (String) data.get(i)[0];
		}
		return "0";
	}

	@Override
	public int getSize() {
		return data.size();
	}
	public void getData(){
		if(condition == null) getData(table, sortColumn, columns) ; else getData(table, condition, sortColumn, columns);
	}
	public void getData(String tableName, int sortCol, String... col){
		try {
			dbCon = DBController.getInstance();
			data.clear();
			List<Object[]> result = dbCon.selectNoDupe(tableName, null, sortCol, true, col);
			
			for(Object[]  i : result){
				data.add(i);
			}
			fireContentsChanged(this, 0, data.size());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	public void getData(String tableName, String cond, int sortCol, String... col){
		try {
			dbCon = DBController.getInstance();
			data.clear();
			List<Object[]> result = dbCon.selectNoDupe(tableName, cond, sortCol, true, col);
			
			for(Object[]  i : result){
				data.add(i);
			}
			fireContentsChanged(this, 0, data.size());
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int insertElement(String table, String...values ){
		try {
			dbCon = DBController.getInstance();
			int id = dbCon.insertInto(table, values);
			data.clear();
			data.add(values);
			fireContentsChanged(this, 0, data.size());
			return id;
		}catch (SQLException | ClassNotFoundException e2){
		
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
			System.exit(-1);
		}
		return -1;
	}
	public List<Object[]> getAllItems(){
		return data;
	}
	
	

}

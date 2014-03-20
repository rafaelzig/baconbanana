package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class SQLList extends AbstractListModel<String>{
	
	private List<String[]> data;
	private String table;
	private String[] columns;
	private int sortColumn;
	private DBController dbCon;
	private ResultSet rs;
	private String condition;
	
	
	//get ID at some point
	public SQLList(String tableName, int sortCol, String... col){
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		sortColumn = sortCol;
		try {
			dbCon = DBController.getInstance();
			dbCon.loadResources();
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
		getData(table, columns);
	}
	
	public SQLList(String tableName, String condition, int sortCol, String...col){
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		this.condition = condition;
		sortColumn = sortCol;
		try {
			dbCon = DBController.getInstance();
			dbCon.loadResources();
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}finally{
			try {
				dbCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getData(table, columns);
	}
	
	@Override
	public String getElementAt(int i) {
		return data.get(i)[sortColumn];
	}
	
	public int getId(int i){
		return Integer.parseInt(data.get(i)[0]);
	}

	@Override
	public int getSize() {
		return data.size();
	}
	
	public void getData(String tableName, String... col){
		try {
			List<Object[]> result = dbCon.select(table, sortColumn, true, col);
			int count = 0;
			for(Object[]  i : result){
				String[] item = (String[]) i[count++];
				data.add(item);
			}
		} catch (SQLException | InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getData(String tableName, String cond, String... col){
		try {
			List<Object[]> result = dbCon.select(table, cond, sortColumn, true, col);
			int count = 0;
			for(Object[]  i : result){
				String[] item = (String[]) i[count++];
				data.add(item);
			}
		} catch (SQLException | InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

}

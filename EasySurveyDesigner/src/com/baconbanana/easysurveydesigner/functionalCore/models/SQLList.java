package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

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
		try {
			dbCon = DBController.getInstance();
			dbCon.loadResources();
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public SQLList(String tableName, String condition, int sortCol, String...col){
		super();
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
	}
	
	@Override
	public String getElementAt(int i) {
		return (String) data.get(i)[sortColumn];
	}
	
	public int getId(int i){
		return (int) data.get(i)[0];
	}

	@Override
	public int getSize() {
		return data.size();
	}
	
	public void getData(String tableName, int sortCol, String... col){
		try {
			try {
				dbCon = DBController.getInstance();
				dbCon.loadResources();
			} catch (ClassNotFoundException | SQLException e) {
			
				e.printStackTrace();
			}
			List<Object[]> result = dbCon.select(table, sortCol, true, col);
			
			for(Object[]  i : result){
				data.add(i);
			}
		} catch (SQLException | InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				dbCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void getData(String tableName, String cond, int sortCol, String... col){
		try {
			try {
				dbCon = DBController.getInstance();
				dbCon.loadResources();
			} catch (ClassNotFoundException | SQLException e) {
			
				e.printStackTrace();
			}
			List<Object[]> result = dbCon.select(table, cond, sortCol, true, col);
			
			for(Object[]  i : result){
				data.add(i);
			}
		} catch (SQLException | InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				dbCon.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void insertElement(String table, String...values ){
		try {
			try {
				dbCon.insertInto(table, values);
			}catch (InvalidStateException e1) {
				e1.printStackTrace();
			}finally{
				if (dbCon != null)
					dbCon.close();
			}
			data.add(values);
		}catch (SQLException e2){
		
			e2.printStackTrace();
			System.err.println(e2.getClass().getName() + " : " + e2.getMessage());
			System.exit(-1);
		}
	}
	
	

}

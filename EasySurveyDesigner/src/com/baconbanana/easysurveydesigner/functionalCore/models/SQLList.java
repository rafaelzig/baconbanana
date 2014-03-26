package com.baconbanana.easysurveydesigner.functionalCore.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
/**
 * A model for SQL lists, controls the views and database
 * @author Control
 *
 */
public class SQLList extends AbstractListModel{
	
	private List<Object[]> data;
	private String table;
	private String[] columns;
	private int sortColumn;
	private DBController dbCon;
	private String condition;
	
	
	/**
	 * Constructor for lists without conditions
	 * @param tableName
	 * @param sortCol Column of the result that the results will be displayed and oredered by
	 * @param col Columns that will be recived from database
	 */
	public SQLList(String tableName, int sortCol, String... col){
		super();
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		sortColumn = sortCol;
	}
	/**
	 * Constructor for lists with a condition
	 * @param tableName
	 * @param condition Condition which the database will be filtered by
	 * @param sortCol Column of the result that the results will be displayed and oredered by
	 * @param col Columns that will be recived from database
	 */
	public SQLList(String tableName, String condition, int sortCol, String...col){
		super();
		data = new LinkedList<>();
		table = tableName;
		columns = col;
		this.condition = condition;
		sortColumn = sortCol;
	}
	/**
	 * returns elent that will be displayed by list widget
	 */
	@Override
	public String getElementAt(int i) {
		return (String) data.get(i)[sortColumn];
	}
	/**
	 * Returns the if of the the object
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
	/**
	 * Helper methord that does not require teble specification
	 * Decision made depending on wether condition is given
	 */
	public void getData(){
		if(condition == null) getData(table, sortColumn, columns) ; else getData(table, condition, sortColumn, columns);
	}
	/**
	 * Refresh a list without a condition
	 * @param tableName
	 * @param sortCol Column of the result that the results will be displayed and oredered by
	 * @param col Columns that will be recived from database
	 */
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
	/**
	 * Refresh a list with a condition
	 * @param tableName
	 * @param condition Condition which the database will be filtered by
	 * @param sortCol Column of the result that the results will be displayed and oredered by
	 * @param col Columns that will be recived from database
	 */
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
	/**
	 * This methord inserts an elemet into the list and into the database
	 * @param table name of table
	 * @param values values to be inserted
	 * @return
	 */
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

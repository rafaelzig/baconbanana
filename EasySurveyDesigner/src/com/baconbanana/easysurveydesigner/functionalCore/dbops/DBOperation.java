package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DBOperation {
	
	private static Connection con;
	
	public static Connection getConnect(){
		if (con == null){
			String systemDir = System.getenv("USERPROFILE");
			try{
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:"+ systemDir +"\\My Documents\\SQLite\\easysurvey.db");
				Statement s = con.createStatement();
				s.execute("PRAGMA foreign_keys = ON");
				s.close();
			}catch (Exception e){
				e.printStackTrace();
				System.err.println(e.getClass().getName() + " : " + e.getMessage());
				System.exit(0);
			}
		}
		return con;
	}
	
	public static boolean createTable(String sql){
		try{
			executeStatement(sql);
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
	public static boolean insertRecord(String sql){
		try{
			executeStatement(sql);
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return false;
		}
	}
	public static int insertRecordReturnID(String sql){
		try{
			executeStatement(sql);
			sql = "SELECT last_insert_rowID()";
			ArrayList<String[]> lastRow = selectRecord(sql);
			return Integer.parseInt(lastRow.get(0)[0]);
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return -1;
		}
	}
	//questionable output
	public static ArrayList<String[]> selectRecord(String sql){
		Connection c = getConnect();
		Statement s;
		ResultSet rs;
		ResultSetMetaData rsmd;
		ArrayList<String[]> results = new ArrayList<String[]>();
		try{
			c.setAutoCommit(false);
			s = c.createStatement();
			rs = s.executeQuery(sql);
			rsmd = rs.getMetaData();
			while(rs.next()){
				int colCount = rsmd.getColumnCount();
				String[] row = new String[colCount];
				System.out.println(colCount);
				for(int i = 0; i < colCount; i++){
					row[i] = rs.getString(i);
				}
				results.add(row);
			}
		}catch (SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(0);
		}
		return results;
	}
	
	public static boolean exists(String table){
		Connection c = getConnect();
		Statement s = null;
		try{
			s = c.createStatement();
			s.executeUpdate("SELECT * FROM " + table);
			s.close();
		}catch(SQLException e){
			return false;
		}
		return true;
	}
	
	private static void executeStatement(String stmt)throws SQLException{
		Connection c = getConnect();
		Statement s = null;
		s = c.createStatement();
		s.executeUpdate(stmt);
		s.close();
	}
	
	
}

package com.baconbanana.easysurveydesigner.functionalCore.dbops.old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/*
 * This class is not for use it is an exsample of concurancy, it is shit
 */

public class DBOperationOldv2 {
	
	private static Connection con;
	private static LinkedList<Thread> cmdLine = new LinkedList<Thread>();
	
	public static Connection getConnect(){
		if (con == null){
			String osName = System.getProperty("os.name");
			String systemDir = "";
			if(osName.contains("Windows")){
				systemDir = System.getenv("USERPROFILE");
			}else if(osName.contains("Mac")){
				systemDir = System.getenv("HOME");
			}
			try{
				Class.forName("org.sqlite.JDBC");
				//That is the line that creates the database.
				if(osName.contains("Windows")){
					con = DriverManager.getConnection("jdbc:sqlite:"+ systemDir +"\\My Documents\\SQLite\\easysurvey.db");
				}else if(osName.contains("Mac")){
					con = DriverManager.getConnection("jdbc:sqlite:"+ systemDir +"/Documents/SQLite/easysurvey.db");
				}
				con.setAutoCommit(false);
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
		final String stmt = "CREATE TABLE " + sql;
		Thread createTableThread = new Thread(){			
			public void run(){
				Connection c = getConnect();
				Statement s = null;
				try {
					s = c.createStatement();
					s.executeUpdate(stmt);
					s.close();
					c.commit();
				} catch (SQLException e) {
					System.out.println("An error acoured in the DBStatement thread " + this.getId() + " : " + this.getName());
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		createTableThread.start();
		try {
			createTableThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public static boolean insertRecord(String sql){
		final String stmt = "INSERT INTO " + sql;
		Thread insertThread = new Thread(){			
			public void run(){
				Connection c = getConnect();
				Statement s = null;
				try {
					s = c.createStatement();
					s.executeUpdate(stmt);
					s.close();
					c.commit();
				} catch (SQLException e) {
					System.out.println("An error acoured in the DBStatement thread " + this.getId() + " : " + this.getName());
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		};
		insertThread.start();
		try {
			insertThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/*public static boolean deleteRecord(String sql){
		try{
			executeStatement("DELETE from " + sql);
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return false;
		}
	}
	
	public static boolean onUpdate(String sql){
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
			return Integer.parseInt(lastRow.get(0)[1]);
		}catch(SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return -1;
		}
	}*/
	//questionable output
	public static ArrayList<String[]> selectRecord(String sql){
		final String stmt = "SELECT * FROM " + sql;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String[]> results = new ArrayList<String[]>();
		Callable<ResultSet> selectCallable = new Callable<ResultSet>(){			
			public ResultSet call(){
				Connection c = getConnect();
				Statement s;
				ResultSet rs = null;
				try{
					s = c.createStatement();
					rs = s.executeQuery(stmt);
				}catch (SQLException e){
					e.printStackTrace();
					System.err.println(e.getClass().getName() + " : " + e.getMessage());
					System.exit(0);
				}
				return rs;
		}};
		try {
			ExecutorService pool = Executors.newSingleThreadExecutor();
			Future<ResultSet> future = pool.submit(selectCallable);
			rs = future.get();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(rs.next()){
				int colCount = rsmd.getColumnCount();
				String[] row = new String[colCount];
				System.out.println(colCount);
				for(int i = 1; i < colCount; i++){
					row[i] = rs.getString(i);
				}
				results.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	//--------------------------------------------------------------------------------------------------------------------------
	//I have created those methods to compare username with data from database but they did not work perfectly. 
	//They are not in use at the moment but I left them here in case they would be useful in future.
	public static ArrayList<String> selectRecord2(String sql, String colName){
		Connection c = getConnect();
		Statement s;
		ResultSet rs;
		ArrayList<String> results = new ArrayList<String>();
		try{
			c.setAutoCommit(false);
			s = c.createStatement();
			rs = s.executeQuery(sql);
			
			while(rs.next()){
				String data = rs.getString(colName);
				results.add(data);
				System.out.println(data);
				
			}
		}catch (SQLException e){
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(0);
		}
		return results;
	}	
	
	public static String checkPassword2(){
		Connection c = getConnect();
		String s = null;
		try {
			PreparedStatement st = con.prepareStatement("SELECT Username FROM Login WHERE Username = 'Barry'");
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				String s1 = rs.getString(1);
				return s = s1 ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(0);
		}
		
		return s;
		
	}
	//TO DO change to throws SQL exception
	public static boolean existsRecord(String sql){
		ArrayList<String[]> result = selectRecord(sql);
		if(result.size() > 0){
			return true;
			
		}
		else{
			return false;
			}
	}
	
}

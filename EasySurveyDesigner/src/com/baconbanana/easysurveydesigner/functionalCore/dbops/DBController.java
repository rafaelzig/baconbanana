package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.baconbanana.easysurveydesigner.DBTest;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class DBController
{
	private static final String DB_NAME = "easysurvey";
	private static final String MAC_DB_PATH = "/Documents/SQLite/" + DB_NAME
			+ ".db";
	private static final String MAC_SYS_DIR = System.getenv("HOME");
	private static final String WIN_DB_PATH = "\\My Documents\\SQLite\\"
			+ DB_NAME + ".db";
	private static final String WIN_SYS_DIR = System.getenv("USERPROFILE");
	private static DBController instance = null;

	private Connection conn;
	private Statement genericStatement;
	private boolean isReady = false;

	/**
	 * TODO
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static DBController getInstance() throws ClassNotFoundException,
			SQLException
	{
		return (instance == null) ? new DBController() : instance;
	}

	/**
	 * TODO
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		closeResources();
		isReady = false;
		instance = null;
	}

	/**
	 * TODO
	 * 
	 * @throws SQLException
	 */
	private void closeResources() throws SQLException
	{
		if (genericStatement != null)
			genericStatement.close();
		if (conn != null)
			conn.close();
	}

	/**
	 * TODO
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private DBController() throws ClassNotFoundException, SQLException
	{
		super();
		openConnection();
	}

	/**
	 * TODO
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void openConnection() throws ClassNotFoundException, SQLException
	{
		String connString = "jdbc:sqlite:";
		String osName = System.getProperty("os.name");

		if (osName.contains("Windows"))
			connString += WIN_SYS_DIR + WIN_DB_PATH;
		else if (osName.contains("Mac"))
			connString += MAC_SYS_DIR + MAC_DB_PATH;

		Class.forName("org.sqlite.JDBC");

		// conn = DriverManager.getConnection(connString);
		conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME + ".db");
	}

	public void prepareDB() throws SQLException
	{
		genericStatement = conn.createStatement();
		genericStatement.execute("PRAGMA foreign_keys = ON");
		genericStatement.executeUpdate("drop table if exists " + DBTest.TABLE_NAME + ";");

		isReady = true;
	}

	/**
	 * TODO
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int createTable(String tableName, Map<String, String> parameters)
			throws SQLException, InvalidStateException
	{
		// CREATE TABLE table_name
		// (
		// column_name1 data_type(size),
		// column_name2 data_type(size),
		// column_name3 data_type(size),
		// ....
		// );

		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "CREATE TABLE " + tableName + " ( ";
			sql += prepareSql(parameters, " ");
			sql += " );";

			return genericStatement.executeUpdate(sql);
		}
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int insertInto(String tableName, List<String> columns,
			List<String> values) throws SQLException, InvalidStateException
	{
		// INSERT INTO table_name (column1,column2,column3,...)
		// VALUES (value1,value2,value3,...);

		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "INSERT INTO " + tableName;

			if (columns != null)
				sql += prepareSql(columns, true);

			sql += " VALUES " + prepareSql(values, true) + ";";

			return genericStatement.executeUpdate(sql);

		}

		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int insertInto(String tableName, List<String> values)
			throws SQLException, InvalidStateException
	{
		// INSERT INTO table_name
		// VALUES (value1,value2,value3,...);

		return insertInto(tableName, null, values);
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @param value
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public ResultSet select(String tableName, List<String> columns,
			String condition) throws SQLException, InvalidStateException
	{
		// SELECT column_name,column_name
		// FROM table_name
		// WHERE condition;

		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "SELECT " + prepareSql(columns, false);
			sql += " FROM " + tableName;

			if (condition != null && !condition.isEmpty())
				sql += " WHERE " + condition + ";";

			return genericStatement.executeQuery(sql);
		}

		return null;
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @param value
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public ResultSet select(String tableName, List<String> columns)
			throws SQLException, InvalidStateException
	{
		// SELECT column_name,column_name
		// FROM table_name;

		return select(tableName, columns, null);
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public ResultSet selectAll(String tableName) throws SQLException,
			InvalidStateException
	{
		// SELECT * FROM table_name;

		String sql = "SELECT * FROM " + tableName + ";";
		return genericStatement.executeQuery(sql);
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @param value
	 * @param criteriaCol
	 * @param criteriaValue
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int update(String tableName, Map<String, String> parameters,
			String condition) throws SQLException, InvalidStateException
	{
		// UPDATE table_name
		// SET column1=value1,column2=value2,...
		// WHERE condition;

		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "UPDATE " + tableName;
			sql += " SET " + prepareSql(parameters, "=");

			if (condition != null)
				sql += " WHERE " + condition;

			sql += ";";

			return genericStatement.executeUpdate(sql);
		}
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param tableName
	 * @param parameters
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int updateAll(String tableName, Map<String, String> parameters)
			throws SQLException, InvalidStateException
	{
		// UPDATE table_name
		// SET column1=value1,column2=value2,...

		return update(tableName, parameters, null);
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int delete(String tableName, String criteria) throws SQLException,
			InvalidStateException
	{
		// DELETE FROM table_name
		// WHERE some_column=some_value;

		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "DELETE FROM " + tableName;

			if (criteria != null)
				sql += " WHERE " + criteria;

			sql += ";";

			return genericStatement.executeUpdate(sql);
		}
		return -1;
	}

	/**
	 * TODO
	 * 
	 * @param colName
	 * @param colValue
	 * @return
	 * @throws SQLException
	 * @throws InvalidStateException
	 */
	public int deleteAll(String tableName) throws SQLException,
			InvalidStateException
	{
		// DELETE FROM table_name

		return delete(tableName, null);
	}

	/**
	 * TODO
	 * 
	 * @param columns
	 * @param addBrackets
	 * @return
	 */
	private String prepareSql(List<String> columns, boolean addBrackets)
	{
		int count = columns.size();

		String sql = (addBrackets) ? "(" : new String();

		for (String col : columns)
		{
			sql += col;
			if (--count > 0)
				sql += ",";
		}

		return (addBrackets) ? sql + ")" : sql;
	}

	/**
	 * TODO
	 * 
	 * @param parameters
	 * @param separator
	 * @return
	 */
	private String prepareSql(Map<String, String> parameters, String separator)
	{
		String sql = new String();
		int count = parameters.entrySet().size();

		for (Entry<String, String> param : parameters.entrySet())
		{
			sql += param.getKey() + separator + param.getValue();
			if (--count > 0)
				sql += ",";
		}

		return sql;
	}

	/**
	 * TODO
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public void printResult(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();

		System.out.println("SELECT STATEMENT :");

		while (rs.next())
		{
			for (int i = 1; i <= columnsNumber; i++)
			{
				if (i > 1)
					System.out.print(" - ");
				System.out
						.print(rsmd.getColumnName(i) + ": " + rs.getString(i));

			}

			System.out.println();
		}
	}
}
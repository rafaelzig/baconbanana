package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * Singleton Class used to perform Database operations.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class DBController
{
	private static final String DB_NAME = "easysurvey.db";
	private static final File MAC_WORKING_DIR = new File(System.getenv("HOME")
			+ "/Documents/SQLite");
	private static final File WIN_WORKING_DIR = new File(
			System.getenv("USERPROFILE") + "\\My Documents\\SQLite");

	private static DBController instance = null;
	private Connection conn;
	private Statement genericStatement;
	private PreparedStatement selectGeneratedIdStatement, existsStatement;
	private boolean isReady = false;

	/**
	 * Returns the instance of this singleton class.
	 * 
	 * @return The instance of this singleton class.
	 */
	public static DBController getInstance() throws ClassNotFoundException,
			SQLException
	{
		return (instance == null) ? new DBController() : instance;
	}

	/**
	 * Private constructor of this singleton class.
	 * 
	 * @throws ClassNotFoundException
	 *             Signals an error that no definition for the class with the
	 *             specified name could be found.
	 * @throws SQLException
	 *             Signals an error has occurred when closing the sqlite
	 *             resources.
	 */
	private DBController() throws ClassNotFoundException, SQLException
	{
		super();
		openConnection();
	}

	/**
	 * Closes the resources associated with this singleton class.
	 */
	public void close() throws SQLException
	{
		try
		{
			if (genericStatement != null)
				genericStatement.close();
			if (selectGeneratedIdStatement != null)
				selectGeneratedIdStatement.close();
			if (existsStatement != null)
				existsStatement.close();
		}
		finally
		{
			if (conn != null)
				conn.close();
		}

		isReady = false;
		instance = null;
	}

	/**
	 * Opens the database SQLite connection.
	 */
	private void openConnection() throws ClassNotFoundException, SQLException
	{
		String osName = System.getProperty("os.name");
		String connString = "jdbc:sqlite:";

		if (osName.contains("Windows"))
		{
			WIN_WORKING_DIR.mkdirs();
			connString += WIN_WORKING_DIR + "\\";
		}
		else if (osName.contains("Mac"))
		{
			MAC_WORKING_DIR.mkdirs();
			connString += MAC_WORKING_DIR + "/";
		}

		Class.forName("org.sqlite.JDBC");

		conn = DriverManager.getConnection(connString + DB_NAME);
	}

	/**
	 * Loads the required SQLite resources for this Singleton class to function.
	 */
	public void loadResources() throws SQLException
	{
		genericStatement = conn.createStatement();
		genericStatement.execute("PRAGMA foreign_keys = ON");

		selectGeneratedIdStatement = conn
				.prepareStatement("SELECT last_insert_rowid();");
		existsStatement = conn
				.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=?;");

		isReady = true;
	}

	/**
	 * Creates a Database table with the specified table name and parameters.
	 * 
	 * @see Example: CREATE TABLE table_name ( column_name1 data_type(size),
	 *      column_name2 data_type(size), column_name3 data_type(size), .... );
	 * @param tableName
	 *            String object representing the table name.
	 * @param parameters
	 *            Map<String, String> containing the key values representing the
	 *            parameters.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int createTable(String tableName, Map<String, String> parameters)
			throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "CREATE TABLE " + tableName + " ( ";
			sql += prepareSql(parameters, " ");
			sql += " );";

			return genericStatement.executeUpdate(sql);
		}
		return 0;
	}

	/**
	 * Inserts the specified values into the specified columns into the database
	 * table with the specified name.
	 * 
	 * @see Example: INSERT INTO tableName (column1,column2,column3,...) VALUES
	 *      (value1,value2,value3,...);
	 * @param tableName
	 *            String object representing the table name.
	 * @param columns
	 *            List of String objects representing the column names in which
	 *            the values will be inserted.
	 * @param values
	 *            List of String objects representing the values to be inserted.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int insertInto(String tableName, List<String> columns,
			List<String> values) throws SQLException, InvalidStateException
	{
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

		return 0;
	}

	/**
	 * Inserts the specified values into the database table with the specified
	 * name.
	 * 
	 * @see Example: INSERT INTO tableName VALUES (value1,value2,value3,...);
	 * 
	 * @param tableName
	 *            String object representing the table name.
	 * @param values
	 *            List of String objects representing the values to be inserted.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int insertInto(String tableName, List<String> values)
			throws SQLException, InvalidStateException
	{
		return insertInto(tableName, null, values);
	}

	/**
	 * Gets the result of the SQL Select statement on the database with the
	 * specified table name, columns and condition.
	 * 
	 * @see Example: SELECT column1, column2 FROM tableName WHERE condition;
	 * @param tableName
	 *            String object representing the table name.
	 * @param columns
	 *            List of String objects representing the column names in which
	 *            the values will be inserted.
	 * @param condition
	 *            String object representing the condition applied on the SELECT
	 *            statement.
	 * @return ResultSet object that contains the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public ResultSet select(String tableName, List<String> columns,
			String condition) throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "SELECT " + prepareSql(columns, false);
			sql += " FROM " + tableName;

			if (condition != null && !condition.isEmpty())
				sql += " WHERE " + condition + ";";

			List<Object[]> test = prepareResult(genericStatement
					.executeQuery(sql));

		}

		return null;
	}

	/**
	 * TODO
	 * 
	 * @throws SQLException
	 */
	public List<Object[]> prepareResult(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int columnCount = rsMetaData.getColumnCount();

		ArrayList<Object[]> table = new ArrayList<Object[]>();
		Object[] header = new String[columnCount];

		Object columnHeader;

		for (int i = 1; i <= columnCount; ++i)
		{
			columnHeader = rsMetaData.getColumnLabel(i);
			header[i - 1] = columnHeader;
		}

		table.add((Object[]) header);

		Object[] tuple = null;

		while (rs.next())
		{
			tuple = (Object[]) Operations.getArray(tuple.getClass(), columnCount);

			Object value;

			for (int i = 1; i <= columnCount; ++i)
			{
				value = (Object) rs.getObject(i);
				tuple[i - 1] = value;
			}

			table.add(tuple);
		}

		rs.close();

		return table;
	}

	/**
	 * Gets the result of the SQL Select statement on the database with the
	 * specified table name and columns.
	 * 
	 * @see Example: SELECT column1, column2 FROM tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @param columns
	 *            List of String objects representing the column names in which
	 *            the values will be inserted.
	 * @return ResultSet object that contains the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public ResultSet select(String tableName, List<String> columns)
			throws SQLException, InvalidStateException
	{
		return select(tableName, columns, null);
	}

	/**
	 * Gets the all values from the database table with the specified table
	 * name.
	 * 
	 * @see Example: SELECT * FROM tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @return ResultSet object that contains the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public ResultSet selectAll(String tableName) throws SQLException,
			InvalidStateException
	{
		// SELECT * FROM table_name;

		String sql = "SELECT * FROM " + tableName + ";";
		return genericStatement.executeQuery(sql);
	}

	/**
	 * Checks if the database table with the specified table name exists or not.
	 * 
	 * @param tableName
	 *            String object representing the table name.
	 * @return true if the database table with the specified table name exists,
	 *         false otherwise.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public boolean exists(String tableName) throws SQLException,
			InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		existsStatement.setString(1, tableName);

		ResultSet rs = existsStatement.executeQuery();

		boolean exists = rs.next();

		rs.close();

		return exists;
	}

	/**
	 * Updates the database table with the specified table name, with the
	 * specified parameters and condition.
	 * 
	 * @see Example: UPDATE tableName SET column1=value1,column2=value2,...
	 *      WHERE condition;
	 * @param tableName
	 *            String object representing the table name.
	 * @param parameters
	 *            Map<String, String> containing the key values representing the
	 *            parameters of this SQL UPDATE statement.
	 * @param condition
	 *            String object representing the condition to be applied on this
	 *            SQL UPDATE statement.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int update(String tableName, Map<String, String> parameters,
			String condition) throws SQLException, InvalidStateException
	{
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
		return 0;
	}

	/**
	 * Updates all the values of the database table with the specified table
	 * name, with the specified parameters.
	 * 
	 * @see Example: UPDATE tableName SET column1=value1,column2=value2,...;
	 * @param tableName
	 *            String object representing the table name.
	 * @param parameters
	 *            Map<String, String> containing the key values representing the
	 *            parameters of this SQL UPDATE statement.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int updateAll(String tableName, Map<String, String> parameters)
			throws SQLException, InvalidStateException
	{
		return update(tableName, parameters, null);
	}

	/**
	 * Deletes the rows of the database table with the specified table name,
	 * which meet the specified criteria.
	 * 
	 * @see Example: DELETE FROM tableName WHERE condition;
	 * @param tableName
	 *            String object representing the table name.
	 * @param condition
	 *            String object representing the condition to be applied on this
	 *            SQL UPDATE statement.
	 * 
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int delete(String tableName, String condition) throws SQLException,
			InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "DELETE FROM " + tableName;

			if (condition != null)
				sql += " WHERE " + condition;

			sql += ";";

			return genericStatement.executeUpdate(sql);
		}
		return 0;
	}

	/**
	 * Deletes all rows of the database table with the specified table name,
	 * 
	 * @see Example: DELETE FROM tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int deleteAll(String tableName) throws SQLException,
			InvalidStateException
	{
		return delete(tableName, null);
	}

	/**
	 * Deletes the database table with the specified table name.
	 * 
	 * @see Example: DROP TABLE tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int deleteTable(String tableName) throws SQLException,
			InvalidStateException
	{
		return drop(tableName, true);
	}

	/**
	 * Deletes the database table with the specified table name.
	 * 
	 * @see Example: DROP DATABASE databaseName;
	 * @param databaseName
	 *            String object representing the database name.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public int deleteDatabase(String databaseName) throws SQLException,
			InvalidStateException
	{
		return drop(databaseName, false);
	}

	/**
	 * Helper method which deletes either the database with the specified name
	 * or the database table with the specified name.
	 * 
	 * @param name
	 *            String object representing the resource name to be deleted.
	 * @param isTable
	 *            Boolean representing wheter or not the resource to be deleted
	 *            is a table.
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	private int drop(String name, boolean isTable)
			throws InvalidStateException, SQLException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (name != null && !name.isEmpty())
		{
			String sql = (isTable) ? "DROP TABLE " : "DROP DATABASE ";

			sql += name + ";";

			return genericStatement.executeUpdate(sql);
		}

		return 0;
	}

	/**
	 * Retrieves any auto-generated keys created as a result of the last SQL
	 * INSERT statement. If this statement did not generate any keys, an empty
	 * ResultSet object is returned.
	 * 
	 * @return ResultSet object containing the auto-generated key(s) generated
	 *         by the execution of the last SQL INSERT statement.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public ResultSet getLastGeneratedKey() throws SQLException,
			InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		return selectGeneratedIdStatement.executeQuery();
	}

	/**
	 * Helper method which prepares the SQL string to be used in the Statement
	 * object.
	 * 
	 * @param columns
	 *            List of String objects representing the columns.
	 * @param addBrackets
	 *            Whether or not the String object to be returned should be
	 *            enclosed by brackets.
	 * @return String object containing the prepared SQL statement.
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
	 * Helper method which prepares the SQL string to be used in the Statement
	 * object.
	 * 
	 * @param parameters
	 *            Map<String, String> containing the key values representing the
	 *            parameters.
	 * @param separator
	 *            String object representing the separator to be used between
	 *            the parameters.
	 * @return String object containing the prepared SQL statement.
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
	 * Convenience method which takes a ResultSet object as argument and prints
	 * the results, line by line, to the console.
	 * 
	 * @param rs
	 *            ResultSet object containing the result to be printed.
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

		rs.close();
	}
}
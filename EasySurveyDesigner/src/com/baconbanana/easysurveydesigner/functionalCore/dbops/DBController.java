package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

/**
 * Singleton Class used to perform Database operations.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class DBController
{
	public static final String DB_NAME = "easysurvey.db";
	private static final File MAC_WORKING_DIR = new File(System.getenv("HOME")
			+ "/Documents/SQLite");
	private static final File WIN_WORKING_DIR = new File(
			System.getenv("USERPROFILE") + "\\Documents\\SQLite");

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
		if (!isReady)
		{
			genericStatement = conn.createStatement();
			genericStatement.execute("PRAGMA foreign_keys = ON");

			selectGeneratedIdStatement = conn
					.prepareStatement("SELECT last_insert_rowid();");
			existsStatement = conn
					.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=?;");

			isReady = true;
		}
	}

	/**
	 * Creates all Database Tables if they don't already exist, returning the
	 * row count;
	 * 
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int createAllTables() throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		conn.setAutoCommit(false);
		int count = 0;

		for (Table table : Table.values())
		{
			String tableName = table.getName();

			if (!exists(tableName))
			{
				if (createTable(tableName, table.getParameters()))
					count++;
			}
		}

		if (isTableEmpty(Table.TYPE.getName()))
			populateTable(Table.TYPE.getName());

		conn.commit();
		conn.setAutoCommit(true);

		return count;
	}

	/**
	 * Auxiliary method which populates the specified database table with the
	 * initial values defined on the enum class.
	 * 
	 * @param tableName
	 *            String object representing the database table name.
	 */
	private void populateTable(String tableName) throws SQLException,
			InvalidStateException
	{
		for (QuestionType type : QuestionType.values())
			insertInto(tableName, "'" + type.toString() + "'");
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
	 * @return True if the database table has been created successfully, false
	 *         otherwise.
	 */
	public boolean createTable(String tableName, Map<String, String> parameters)
			throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "CREATE TABLE " + tableName + " ( ";
			sql += prepareSql(parameters, " ");
			sql += " );";

			genericStatement.executeUpdate(sql);

			return true;
		}

		return false;
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
	 *            Array of String objects representing the column names in which
	 *            the values will be inserted.
	 * @param values
	 *            String objects representing the values to be inserted.
	 * @return Either (1) the generated id for the SQL INSERT statement or (2) 0
	 *         for SQL statements that return nothing.
	 */
	public int insertInto(String tableName, String[] columns, String... values)
			throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "INSERT INTO " + tableName;

			if (columns != null)
				sql += prepareSql(columns, true);

			sql += " VALUES " + prepareSql(values, true) + ";";

			genericStatement.executeUpdate(sql);

			return getLastGeneratedKey();
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
	 *            String objects representing the values to be inserted.
	 * @return Either (1) the generated id for the SQL INSERT statement or (2) 0
	 *         for SQL statements that return nothing.
	 */
	public int insertInto(String tableName, String... values)
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
	 *            String objects representing the column names in which the
	 *            values will be inserted.
	 * @param condition
	 *            String object representing the condition applied on the SELECT
	 *            statement.
	 * @return List of Object arrays containing the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public List<Object[]> select(String tableName, String condition,
			String... columns) throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "SELECT " + prepareSql(columns, false);
			sql += " FROM " + tableName;

			if (condition != null && !condition.isEmpty())
				sql += " WHERE " + condition + ";";

			return prepareResult(genericStatement.executeQuery(sql));
		}

		return null;
	}

	/**
	 * Gets the result of the SQL Select statement on the database with the
	 * specified table name, columns and NO condition, sorted by either
	 * Ascending or Descending.
	 * 
	 * @see Example: SELECT column_name,column_name FROM tableName ORDER BY
	 *      sortColumn ASC|DESC;
	 * 
	 * @param tableName
	 *            String object representing the table name.
	 * @param columnIndex
	 *            Integer representing the column to be used in the sorting.
	 * @param isAscending
	 *            True if the results should be sorted in ascending order, false
	 *            if the results should be sorted in descending order.
	 * @param columns
	 *            String objects representing the column names in which the
	 *            values will be inserted.
	 * @return List of Array objects containing the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public List<Object[]> select(String tableName, int columnIndex,
			boolean isAscending, String... columns) throws SQLException,
			InvalidStateException
	{
		return select(tableName, new String(), columnIndex, isAscending,
				columns);
	}

	/**
	 * Gets the result of the SQL Select statement on the database with the
	 * specified table name, columns and condition, sorted by either Ascending
	 * or Descending.
	 * 
	 * @see Example: SELECT column_name,column_name FROM tableName WHERE
	 *      condition ORDER BY column_name,column_name ASC|DESC;
	 * @param tableName
	 *            String object representing the table name.
	 * @param columns
	 *            String objects representing the column names in which the
	 *            values will be inserted.
	 * @param condition
	 *            String object representing the condition applied on the SELECT
	 *            statement.
	 * @param columnIndex
	 *            Integer representing the column to be used in the sorting.
	 * @param isAscending
	 *            True if the results should be sorted in ascending order, false
	 *            if the results should be sorted in descending order.
	 * @return List of Array Objects containing the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public List<Object[]> select(String tableName, String condition,
			int columnIndex, boolean isAscending, String... columns)
			throws SQLException, InvalidStateException
	{
		String sql;

		sql = (isAscending) ? " ORDER BY " + columns[columnIndex] + " DESC"
				: " ORDER BY " + columns[columnIndex] + " ASC";

		if (condition == null || condition.isEmpty())
			return select(tableName + sql, null, columns);

		return select(tableName, condition, columns);
	}

	/**
	 * Gets the result of the SQL Select statement on the database with the
	 * specified table name and columns.
	 * 
	 * @see Example: SELECT column1, column2 FROM tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @param columns
	 *            String objects representing the column names in which the
	 *            values will be inserted.
	 * @return List of Object arrays containing the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public List<Object[]> select(String tableName, String... columns)
			throws SQLException, InvalidStateException
	{
		return select(tableName, null, columns);
	}

	/**
	 * Gets the all values from the database table with the specified table
	 * name.
	 * 
	 * @see Example: SELECT * FROM tableName;
	 * @param tableName
	 *            String object representing the table name.
	 * @return List of Object arrays containing the data produced by the given
	 *         query, or null if invalid parameters have been passed to this
	 *         method.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public List<Object[]> selectAll(String tableName) throws SQLException,
			InvalidStateException
	{
		String sql = "SELECT * FROM " + tableName + ";";
		return prepareResult(genericStatement.executeQuery(sql));
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
	 * Checks if a row exist in the specified database table with the specified
	 * conditions.
	 * 
	 * @see Example: SELECT EXISTS(SELECT 1 FROM tableName WHERE condition LIMIT
	 *      1)
	 * @param tableName
	 *            String object representing the table name.
	 * @param condition
	 *            String object representing the condition to be applied.
	 * @return true if the row exists in the table, false otherwise.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public boolean exists(String tableName, String condition)
			throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		String sql = "SELECT EXISTS(SELECT 1 FROM " + tableName;

		if (condition != null && !condition.isEmpty())
			sql += " WHERE " + condition;

		sql += " LIMIT 1);";

		ResultSet rs = genericStatement.executeQuery(sql);

		boolean exists = rs.getInt(1) > 0;

		rs.close();

		return exists;
	}

	/**
	 * Checks if the specified Database table is empty.
	 * 
	 * @see Example: SELECT EXISTS(SELECT 1 FROM tableName LIMIT 1)
	 * @param tableName
	 *            String object representing the table name.
	 * @return true if the database table is empty, false otherwise.
	 * @throws InvalidStateException
	 *             Signals an error has occurred when the database resources
	 *             have not been loaded prior to this method call.
	 */
	public boolean isTableEmpty(String tableName) throws SQLException,
			InvalidStateException
	{
		return !exists(tableName, null);
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
	public int deleteAllRows(String tableName) throws SQLException,
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
		if (!isReady)
			throw new InvalidStateException();

		if (tableName != null && !tableName.isEmpty())
			return genericStatement.executeUpdate("DROP TABLE " + tableName
					+ ";");

		return 0;
	}

	/**
	 * Deletes all Database Tables if they already exist. WARNING, THIS
	 * OPERATIONS CANNOT BE REVERSED!!!
	 * 
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int deleteAllTables() throws SQLException, InvalidStateException
	{
		if (!isReady)
			throw new InvalidStateException();

		int count = 0;

		for (Table table : Table.values())
			if (exists(table.getName()))
				count += (deleteTable(table.getName()));

		return count;
	}

	/**
	 * Retrieves any auto-generated keys created as a result of the last SQL
	 * INSERT statement. If this statement did not generate any keys, an empty
	 * ResultSet object is returned.
	 * 
	 * @return Integer containing the auto-generated key generated by the
	 *         execution of the last SQL INSERT statement.
	 */
	public int getLastGeneratedKey() throws SQLException, InvalidStateException
	{
		ResultSet rs = selectGeneratedIdStatement.executeQuery();
		int generatedId = rs.getInt(1);
		rs.close();

		return generatedId;
	}

	/**
	 * Auxiliary method which takes a ResultSet object and parses the result
	 * into a List of Object arrays.
	 * 
	 * @param rs
	 *            ResultSet object containing the data to parse.
	 * @return List of Object arrays containing the result.
	 */
	private List<Object[]> prepareResult(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int columnCount = rsMetaData.getColumnCount();

		List<Object[]> resultTable = new LinkedList<>();

		// String[] header = new String[columnCount];
		//
		// for (int i = 1; i <= columnCount; ++i)
		// header[i - 1] = rsMetaData.getColumnLabel(i);
		//
		// resultTable.add(header);

		Object[] tuple = null;

		while (rs.next())
		{
			tuple = new Object[columnCount];

			for (int i = 1; i <= columnCount; ++i)
				tuple[i - 1] = rs.getObject(i);

			resultTable.add(tuple);
		}

		rs.close();

		return resultTable;
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
	private String prepareSql(String[] columns, boolean addBrackets)
	{
		String sql = (addBrackets) ? "(" : new String();

		for (int index = 0; index < columns.length; index++)
		{
			if (index > 0)
				sql += ",";

			sql += columns[index];
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
	 * Convenience static method which takes a List of Object arrays as argument
	 * and prints the results, line by line, to the console.
	 * 
	 * @param resultTable
	 *            List of Object arrays containing the result.
	 */
	public static void printResult(List<Object[]> resultTable)
			throws SQLException
	{
		for (Object[] row : resultTable)
		{
			for (int i = 0; i < row.length; i++)
			{
				if (i > 0)
					System.out.print("\t\t");

				System.out.print(row[i]);
			}

			System.out.println();
		}
	}
}

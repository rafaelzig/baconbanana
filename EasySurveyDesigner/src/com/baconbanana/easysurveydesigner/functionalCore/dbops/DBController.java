package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;

/**
 * Singleton Class used to perform Database operations.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class DBController
{
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final String DB_NAME = "easysurvey.db";
	public static final File WORKING_DIRECTORY = new File(System.getProperty("user.home") + SEPARATOR + "Documents"
			+ SEPARATOR + "SQLite");
	
	private static DBController instance = null;
	private String connString;

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

		Class.forName("org.sqlite.JDBC");

		WORKING_DIRECTORY.mkdirs();
		
		connString = "jdbc:sqlite:" + WORKING_DIRECTORY + SEPARATOR + DB_NAME;

		try (Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement())
		{
			st.execute("PRAGMA foreign_keys = ON");
		}
	}
	
	/**
	 * Creates all Database Tables if they don't already exist, returning the
	 * row count;
	 * 
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int createAllTables() throws SQLException
	{
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

		return count;
	}

	/**
	 * Auxiliary method which populates the specified database table with the
	 * initial values defined on the enum class.
	 * 
	 * @param tableName
	 *            String object representing the database table name.
	 */
	private void populateTable(String tableName) throws SQLException
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
			throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "CREATE TABLE " + tableName + " ( ";
			sql += prepareSql(parameters, " ");
			sql += " );";

			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement())
			{
				return st.executeUpdate(sql) >= 0;
			}
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
			throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "INSERT INTO " + tableName;

			if (columns != null)
				sql += prepareSql(columns, true);

			sql += " VALUES " + prepareSql(values, true) + ";";

			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement())
			{
				st.executeUpdate(sql);

				try (ResultSet rs = st
						.executeQuery("SELECT last_insert_rowid();"))
				{
					return rs.getInt(1);
				}
			}
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
			throws SQLException
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
	 */
	public List<Object[]> select(String tableName, String condition,
			String... columns) throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "SELECT " + prepareSql(columns, false);
			sql += " FROM " + tableName;

			if (condition != null && !condition.isEmpty())
				sql += " WHERE " + condition + ";";

			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(sql))
			{
				return prepareResult(rs);
			}
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
	 */
	public List<Object[]> select(String tableName, int columnIndex,
			boolean isAscending, String... columns) throws SQLException

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
	 */
	public List<Object[]> select(String tableName, String condition,
			int columnIndex, boolean isAscending, String... columns)
			throws SQLException
	{
		String sql;

		sql = (isAscending) ? " ORDER BY " + columns[columnIndex] + " DESC"
				: " ORDER BY " + columns[columnIndex] + " ASC";

		if (condition == null || condition.isEmpty())
			return select(tableName + sql, null, columns);

		return select(tableName, condition, columns);
	}
	public List<Object[]>selectNoDupe(String tableName, String condition,
			int columnIndex, boolean isAscending, String... columns)
			throws SQLException{
		String sql;

		sql = (isAscending) ? " GROUP BY " + columns[columnIndex] + " ORDER BY " + columns[columnIndex] + " DESC"
				: " ORDER BY " + columns[columnIndex] + " ASC";
		
		if (condition == null || condition.isEmpty())
			return select(tableName + sql, null, columns);

		return select(tableName, condition, columns);
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
	 */
	public List<Object[]> selectAll(String tableName) throws SQLException

	{
		String sql = "SELECT * FROM " + tableName + ";";

		try (Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql))
		{
			return prepareResult(rs);
		}
	}

	/**
	 * Checks if the database table with the specified table name exists or not.
	 * 
	 * @param tableName
	 *            String object representing the table name.
	 * @return true if the database table with the specified table name exists,
	 *         false otherwise.
	 */
	public boolean exists(String tableName) throws SQLException
	{
		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name="
				+ appendApo(tableName) + ";";

		try (Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql))
		{
			return rs.next();
		}
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
	 */
	public boolean exists(String tableName, String condition)
			throws SQLException
	{
		String sql = "SELECT EXISTS(SELECT 1 FROM " + tableName;

		if (condition != null && !condition.isEmpty())
			sql += " WHERE " + condition;

		sql += " LIMIT 1);";

		try (Connection conn = DriverManager.getConnection(connString);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql))
		{
			return rs.getInt(1) > 0;
		}
	}

	/**
	 * Checks if the specified Database table is empty.
	 * 
	 * @see Example: SELECT EXISTS(SELECT 1 FROM tableName LIMIT 1)
	 * @param tableName
	 *            String object representing the table name.
	 * @return true if the database table is empty, false otherwise.
	 */
	public boolean isTableEmpty(String tableName) throws SQLException
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
	 */
	public int update(String tableName, Map<String, String> parameters,
			String condition) throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "UPDATE " + tableName;
			sql += " SET " + prepareSql(parameters, "=");

			if (condition != null)
				sql += " WHERE " + condition;

			sql += ";";

			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement())

			{
				return st.executeUpdate(sql);
			}
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
	 */
	public int updateAll(String tableName, Map<String, String> parameters)
			throws SQLException
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
	 */
	public int delete(String tableName, String condition) throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			String sql = "DELETE FROM " + tableName;

			if (condition != null)
				sql += " WHERE " + condition;

			sql += ";";

			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement())
			{
				return st.executeUpdate(sql);
			}
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
	 */
	public int deleteAllRows(String tableName) throws SQLException
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
	 */
	public int deleteTable(String tableName) throws SQLException
	{
		if (tableName != null && !tableName.isEmpty())
		{
			try (Connection conn = DriverManager.getConnection(connString);
					Statement st = conn.createStatement())
			{
				return st.executeUpdate("DROP TABLE " + tableName + ";");
			}
		}

		return 0;
	}

	/**
	 * Deletes all Database Tables if they already exist. WARNING, THIS
	 * OPERATIONS CANNOT BE REVERSED!!!
	 * 
	 * @return Either (1) the row count for SQL statements or (2) 0 for SQL
	 *         statements that return nothing.
	 */
	public int deleteAllTables() throws SQLException
	{
		int count = 0;

		for (Table table : Table.values())
			if (exists(table.getName()))
				count += (deleteTable(table.getName()));

		return count;
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

	/**
	 * Returns a list a values with apostrophes appended
	 * 
	 * @param values
	 *            values to go into database
	 * @return
	 */
	public static String[] appendApo(String... values)
	{
		String[] retArray = new String[values.length];
		for (int i = 0; i < values.length; i++)
		{
			retArray[i] = "'" + values[i] + "'";
		}
		return retArray;
	}

	public static String appendApo(String values)
	{
		return "'" + values + "'";
	}
}

package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest
{
	public static void main(String[] args) throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");

		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people");
		stat.executeUpdate("create table people (name, occupation)");
		stat.close();

		Runnable tasks[] = { new SQLInsert(conn, "Gandhi", "politics"),
				new SQLSelect(conn, "people"),
				new SQLInsert(conn, "Picaso", "artist"),
				new SQLSelect(conn, "people"),
				new SQLInsert(conn, "shakespeare", "writer"),
				new SQLSelect(conn, "people"),
				new SQLInsert(conn, "tesla", "inventor"),
				new SQLSelect(conn, "people"), };

		System.out.println("Sequential DB access:");

		Thread threads[] = new Thread[tasks.length];
		for (int i = 0; i < tasks.length; i++)
			threads[i] = new Thread(tasks[i]);

		for (int i = 0; i < tasks.length; i++)
		{
			threads[i].start();
			threads[i].join();
		}

		stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM people");
		while (rs.next())
		{
			System.out.println("name = " + rs.getString("name"));
			System.out.println("job = " + rs.getString("occupation"));
		}
		stat.close();
		conn.close();
	}

	private static class SQLInsert implements Runnable
	{
		Connection conn;
		String name, occupation;

		public SQLInsert(Connection conn, String name, String occupation)
		{
			this.conn = conn;
			this.name = name;
			this.occupation = occupation;
		}

		public void run()
		{
			PreparedStatement prep = null;
			long startTime = System.currentTimeMillis();

			try
			{
				try
				{
					prep = conn
							.prepareStatement("insert into people values (?, ?)");

					prep.setString(1, name);
					prep.setString(2, occupation);
					prep.executeUpdate();

					long duration = System.currentTimeMillis() - startTime;
					System.out.println("SQL Insert completed in :" + duration);
				}
				finally
				{
					if (prep != null)
						prep.close();
				}
			}
			catch (SQLException e)
			{
				long duration = System.currentTimeMillis() - startTime;
				System.out.print("SQL Insert failed: " + duration);
				System.out.println("SQLException: " + e);
			}
		}
	}

	private static class SQLSelect implements Runnable
	{
		Connection conn;
		String table;
		ResultSet rs;

		public SQLSelect(Connection conn, String table)
		{
			this.conn = conn;
			this.table = table;
		}

		public void run()
		{
			PreparedStatement prep = null;
			long startTime = System.currentTimeMillis();

			try
			{
				try
				{
					prep = conn.prepareStatement("SELECT * FROM ?");

					prep.setString(1, table);
					rs = prep.executeQuery();

					long duration = System.currentTimeMillis() - startTime;
					System.out.println("SQL SELECT completed in :" + duration);
				}
				finally
				{
					if (prep != null)
						prep.close();
				}
			}
			catch (SQLException e)
			{
				long duration = System.currentTimeMillis() - startTime;
				System.out.print("SQL SELECT failed: " + duration);
				System.out.println("SQLException: " + e);
			}
		}
	}
}
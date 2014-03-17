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

		SqlTask tasks[] = { new SqlTask(conn, "Gandhi", "politics"),
				new SqlTask(conn, "Turing", "computers"),
				new SqlTask(conn, "Picaso", "artist"),
				new SqlTask(conn, "shakespeare", "writer"),
				new SqlTask(conn, "tesla", "inventor"), };

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
		ResultSetMetaData rsmd = rs.getMetaData();
		while (rs.next())
		{
			for (int i = 1; i < rsmd.getColumnCount(); i++)
			{
				System.out.println(rs.getString(i));
			}
		}
		conn.close();
	}

	private static class SqlTask implements Runnable
	{
		Connection conn;
		String name, occupation;

		public SqlTask(Connection conn, String name, String occupation)
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
				System.out.print("   SQL Insert failed: " + duration);
				System.out.println(" SQLException: " + e);
			}
		}
	}
}
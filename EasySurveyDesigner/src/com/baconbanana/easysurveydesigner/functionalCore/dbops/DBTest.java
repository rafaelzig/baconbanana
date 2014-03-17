package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest
{
	public static void main(String[] args) throws Exception
	{
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");

		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people");
		stat.executeUpdate("create table people (name, occupation)");

		SqlTask tasks[] = { new SqlTask(conn, "SELECT * FROM MYTABLE"),
				new SqlTask(conn, "INSERT BLABLA INTO MYTABLE"),
				new SqlTask(conn, "INSERT BLABLA INTO MYTABLE"),
				new SqlTask(conn, "INSERT BLABLA INTO MYTABLE"), };

		System.out.println("Sequential DB access:");

		Thread threads[] = new Thread[tasks.length];
		for (int i = 0; i < tasks.length; i++)
			threads[i] = new Thread(tasks[i]);

		for (int i = 0; i < tasks.length; i++)
		{
			threads[i].start();
			threads[i].join();
		}

		System.out.println("Concurrent DB access:");

		for (int i = 0; i < tasks.length; i++)
			threads[i] = new Thread(tasks[i]);

		for (int i = 0; i < tasks.length; i++)
			threads[i].start();

		for (int i = 0; i < tasks.length; i++)
			threads[i].join();
	}

	private static class SqlTask implements Runnable
	{
		private Connection conn;
		private String statement;

		public SqlTask(Connection connection, String statement)
		{
			this.conn = connection;
			this.statement = statement;
		}

		public void run()
		{
			PreparedStatement ps = null;
			long startTime = System.currentTimeMillis();

			try
			{
				try
				{
					ps = conn.prepareStatement(statement);

					ps.setString(1, statement);
					ps.executeUpdate();

					long duration = System.currentTimeMillis() - startTime;
					System.out.println("SQL Insert completed: " + duration);
				}
				finally
				{
					if (ps != null)
						ps.close();
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
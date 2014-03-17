package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTestTwo
{
	public static void main(String[] args) throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement stat = conn.createStatement();
		stat.executeUpdate("drop table if exists people;");
		stat.executeUpdate("create table people (name, occupation);");
		
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		insert(conn);
		printResult(select(stat));
		
		conn.close();
	}

	private static void printResult(ResultSet rs) throws SQLException
	{
		while (rs.next())
		{
			System.out.println("name = " + rs.getString("name"));
			System.out.println("job = " + rs.getString("occupation"));
		}
		rs.close();
	}

	private static ResultSet select(Statement stat) throws SQLException
	{
		return stat.executeQuery("select * from people where name = 'Gandhi';");
	}

	private static void insert(Connection conn) throws SQLException
	{
		PreparedStatement prep = conn
				.prepareStatement("insert into people values (?, ?);");

		prep.setString(1, "Gandhi");
		prep.setString(2, "politics");
		prep.addBatch();
		prep.setString(1, "Turing");
		prep.setString(2, "computers");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
	}
}
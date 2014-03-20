package com.baconbanana.easysurveydesigner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidStateException;

public class DBTest
{
	public static final String TABLE_NAME = "people";

	public static void main(String[] args)
	{
		DBController controller = null;

		try
		{
			try
			{
				controller = DBController.getInstance();
				controller.loadResources();

//				System.out.println(controller.createAllTables());
//				System.out.println(controller.deleteAllTables());
				
				if (controller.exists(TABLE_NAME))
					controller.deleteTable(TABLE_NAME);

				Map<String, String> param = new HashMap<>();
				param.put("Name", "varchar(20)");
				param.put("Occupation", "varchar(20)");

				controller.createTable(TABLE_NAME, param);

				List<String> values = new LinkedList<>();
				 				values.add("'Rafael'");
				 				values.add("'Student'");
				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, new String[] {
								"Name", "Occupation" }, new String[] {
								"'Joshua'", "'Artist'" }));

				values = new LinkedList<>();
				values.add("'Tommy'");
				values.add("'Developer'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));
				
				controller.delete(TABLE_NAME, "name='Rafael'");

				System.out.println();
				DBController.printResult(controller.selectAll(TABLE_NAME));

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Beka'");
				values.add("'Dancer'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Almira'");
				values.add("'Professional'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				System.out.println();
				DBController.printResult(controller.selectAll(TABLE_NAME));

				values = new LinkedList<>();
				values.add("'Bob'");
				values.add("'Builder'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Wally'");
				values.add("'Spy'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Smith'");
				values.add("'Agent'");

				System.out.println("ID GENERATED:"
						+ controller.insertInto(TABLE_NAME, values));
				

				System.out.println();
				DBController.printResult(controller.selectAll(TABLE_NAME));

				param = new HashMap<String, String>();
				param.put("Name", "'NEO'");
				param.put("Occupation", "'The Chosen One'");

				System.out.println();
				System.out.println(controller.updateAll(TABLE_NAME, param)
						+ " rows changed!");

				System.out.println();
				DBController.printResult(controller.selectAll(TABLE_NAME));

				 controller.deleteAllRows(TABLE_NAME);
				
				 System.out.println();
				 DBController.printResult(controller
				 .selectAll(TABLE_NAME));

			}
			catch (InvalidStateException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				if (controller != null)
					controller.close();
			}

		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(-1);
		}
	}
}

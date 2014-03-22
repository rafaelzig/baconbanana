package com.baconbanana.easysurveydesigner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.baconbanana.easysurveydesigner.functionalCore.dbops.DBController;

public class DBTest
{
	public static final String TABLE_NAME = "people";

	public static void main(String[] args)
	{
		DBController controller = null;

		try
		{
			controller = DBController.getInstance();

//			 System.out.println(controller.createAllTables());
//			 System.out.println(controller.deleteAllTables());

			if (controller.exists(TABLE_NAME))
				controller.deleteTable(TABLE_NAME);

			Map<String, String> param = new HashMap<>();
			param.put("Name", "varchar(20)");
			param.put("Occupation", "varchar(20)");

			controller.createTable(TABLE_NAME, param);

			System.out.println("ID GENERATED:"
					+ controller
							.insertInto(TABLE_NAME, "'Rafael'", "'Student'"));

			System.out.println();
			System.out.println("Is the table empty? "
					+ controller.isTableEmpty(TABLE_NAME));

			System.out.println("Does Rafael exist in the database: "
					+ controller.exists(TABLE_NAME, "Name='Rafael'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, new String[] { "Name",
							"Occupation" }, "'Joshua'", "'Artist'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Tommy'",
							"'Developer'"));

			controller.delete(TABLE_NAME, "name='Rafael'");

			System.out.println();
			DBController.printResult(controller.selectAll(TABLE_NAME));

			System.out.println("ID GENERATED:"
					+ controller
							.insertInto(TABLE_NAME, "'Rafael'", "'Student'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Mitchel'",
							"'Cleaner'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Batman'",
							"'Superhero'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Almira'",
							"'Professional'"));

			System.out.println();
			DBController.printResult(controller.selectAll(TABLE_NAME));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Bob'", "'Builder'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Wally'", "'Spy'"));

			System.out.println("ID GENERATED:"
					+ controller.insertInto(TABLE_NAME, "'Smith'", "'Agent'"));

			System.out.println("Selecting all tuples in table " + TABLE_NAME);
			DBController.printResult(controller.selectAll(TABLE_NAME));

			System.out
					.println("Selecting tuples sorting result by Name in ASC order:");
			DBController.printResult(controller.select(TABLE_NAME, 0, true,
					"Name"));

			System.out
					.println("Selecting tuples sorting result by Name in DESC order:");
			DBController.printResult(controller.select(TABLE_NAME, 0, false,
					"Name"));

			System.out
					.println("Selecting tuples where Occupation is Student, sorting result by Name in ASC order:");
			DBController.printResult(controller.select(TABLE_NAME,
					"Occupation='Student'", 0, true, "Name", "Occupation"));

			System.out
					.println("Selecting tuples where Occupation is Student, sorting result by Name in DESC order:");
			DBController.printResult(controller.select(TABLE_NAME,
					"Occupation='Student'", 0, false, "Name", "Occupation"));

			param = new HashMap<String, String>();
			param.put("Name", "'NEO'");
			param.put("Occupation", "'The Chosen One'");

			System.out.println(controller.updateAll(TABLE_NAME, param)
					+ " rows changed!");

			System.out.println();
			DBController.printResult(controller.selectAll(TABLE_NAME));

			System.out.println();
			System.out.println("Table Rows Deleted: "
					+ controller.deleteAllRows(TABLE_NAME));

			System.out.println();
			System.out.println("Is the table empty? "
					+ controller.isTableEmpty(TABLE_NAME));
		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			System.exit(-1);
		}
	}
}

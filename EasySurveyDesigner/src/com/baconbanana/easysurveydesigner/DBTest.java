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

				if (controller.exists(TABLE_NAME));
					controller.deleteTable(TABLE_NAME);
					
				Map<String, String> param = new HashMap<>();
				param.put("Name", "varchar(20)");
				param.put("Occupation", "varchar(20)");

				controller.createTable(TABLE_NAME, param);

				List<String> values = new LinkedList<>();
				values.add("'Rafael'");
				values.add("'Student'");

				controller.insertInto(TABLE_NAME, values);
				
				System.out.println();
				controller.printResult(controller.getLastGeneratedKey());

				values = new LinkedList<>();
				values.add("'Igor'");
				values.add("'Musician'");

				controller.insertInto(TABLE_NAME, values);

				values = new LinkedList<>();
				values.add("'Tommy'");
				values.add("'Developer'");

				controller.insertInto(TABLE_NAME, values);
				controller.delete(TABLE_NAME, "name='Rafael'");

				System.out.println();
				controller.printResult(controller.selectAll(TABLE_NAME));

				values = new LinkedList<>();
				values.add("'Matt'");
				values.add("'Slacker'");

				controller.insertInto(TABLE_NAME, values);

				values = new LinkedList<>();
				values.add("'Beka'");
				values.add("'Dancer'");

				controller.insertInto(TABLE_NAME, values);

				values = new LinkedList<>();
				values.add("'Almira'");
				values.add("'Professional'");

				controller.insertInto(TABLE_NAME, values);

				System.out.println();
				controller.printResult(controller
						.selectAll(TABLE_NAME));

				values = new LinkedList<>();
				values.add("'Bob'");
				values.add("'Builder'");

				controller.insertInto(TABLE_NAME, values);

				values = new LinkedList<>();
				values.add("'Wally'");
				values.add("'Spy'");

				controller.insertInto(TABLE_NAME, values);

				values = new LinkedList<>();
				values.add("'Smith'");
				values.add("'Agent'");

				
				controller.insertInto(TABLE_NAME, values);

				System.out.println();
				controller.printResult(controller
						.selectAll(TABLE_NAME));
				
				param = new HashMap<String, String>();
				param.put("Name", "'NEO'");
				param.put("Occupation", "'The Chosen One'");
				
				controller.updateAll(TABLE_NAME, param);
				
				System.out.println();
				controller.printResult(controller
						.selectAll(TABLE_NAME));
				
//				controller.deleteAll(TABLE_NAME);
//				
//				System.out.println();
//				controller.printResult(controller
//						.selectAll(TABLE_NAME));
				
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

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

				if (controller.exists(TABLE_NAME))
					controller.deleteTable(TABLE_NAME);
					
				Map<String, String> param = new HashMap<>();
				param.put("Name", "varchar(20)");
				param.put("Occupation", "varchar(20)");

				controller.createTable(TABLE_NAME, param);

				String[] values0  = {"'Rafael'" ,"'Arse'"};

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values0));
				
				System.out.println();
				//controller.printResult(controller.getLastGeneratedKey());

				String[] values1  = {"'Igor'" ,"'Musition'"};
				
				controller.insertInto(TABLE_NAME, values1);
				
				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values1));


				String[] values2  = {"'Tommy'" ,"'Developer'"};

				controller.insertInto(TABLE_NAME, values2);

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values2));

				controller.delete(TABLE_NAME, "name='Rafael'");

				System.out.println();
				controller.printResult(controller.selectAll(TABLE_NAME));

				String[] values3  = {"'Matt'" ,"'Slacker'"};


				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values3));

				controller.insertInto(TABLE_NAME, values3);

				LinkedList<String> values = new LinkedList<>();
				values.add("'Beka'");
				values.add("'Dancer'");

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values));
				
				values = new LinkedList<>();
				values.add("'Almira'");
				values.add("'Professional'");

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values));

				System.out.println();
				controller.printResult(controller
						.selectAll(TABLE_NAME));

				values = new LinkedList<>();
				values.add("'Bob'");
				values.add("'Builder'");

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Wally'");
				values.add("'Spy'");

				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values));

				values = new LinkedList<>();
				values.add("'Smith'");
				values.add("'Agent'");
				
				System.out.println("ID GENERATED:" + controller.insertInto(TABLE_NAME, values));


				System.out.println();
				controller.printResult(controller
						.selectAll(TABLE_NAME));
				
				param = new HashMap<String, String>();
				param.put("Name", "'NEO'");
				param.put("Occupation", "'The Chosen One'");
				
				System.out.println();
				System.out.println(controller.updateAll(TABLE_NAME, param) + " rows changed!");
				
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

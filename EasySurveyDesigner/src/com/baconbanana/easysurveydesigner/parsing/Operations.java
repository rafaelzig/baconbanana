/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * TODO
 * @author Rafael da Silva Costa & Team
 *
 */
public class Operations
{
	/**
	 * TODO
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException
	{
		StringBuilder output = null;
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
			output = new StringBuilder();
			String line;
			
			while ((line = reader.readLine()) != null)
				output.append(line);
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		
		return output.toString();
	}

	/**
	 * TODO
	 * @param input
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeFile(String input, String fileName)
			throws IOException
	{
		BufferedWriter writer = null;
		
		try
		{
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(input);
		}
		finally
		{
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * TODO
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static JSONObject parseJSON(String str) throws ParseException
	{
		JSONParser parser = new JSONParser();

		return (JSONObject) parser.parse(str);
	}
}
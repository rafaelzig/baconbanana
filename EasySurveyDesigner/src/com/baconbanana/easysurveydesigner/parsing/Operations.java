/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
		Path path = new File(fileName).toPath();
		BufferedReader reader = null;
		StringBuilder output = null;
		
		try
		{
			reader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
			output = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
				output.append(line);
		}
		finally
		{
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
		Path path = new File(fileName).toPath();

		BufferedWriter writer = null;

		try
		{
			writer = Files.newBufferedWriter(path, StandardCharsets.US_ASCII);
			writer.write(input);
			writer.close();
		}
		finally
		{
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
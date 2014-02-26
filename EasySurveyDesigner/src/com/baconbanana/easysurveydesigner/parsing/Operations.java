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
 * @author Rafael da Silva Costa & Team
 * 
 *         This class contains static methods for reading and writing to a file,
 *         and for parsing a JSON string into a JSONObject.
 * 
 */
public class Operations
{
	/**
	 * Attempts to read from a file with the specified fileName, returning the
	 * content as a String object.
	 * 
	 * @param fileName
	 *            A String object containing the file name.
	 * @return A String object containing the contents of the file.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
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
	 * Attempts to write the specified String object to a file with the
	 * specified fileName.
	 * 
	 * @param input
	 *            A String object containing the content to be written.
	 * @param fileName
	 *            A String object containing the file name.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
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
	 * Parses the specified json string and returns a JSONObject.
	 * 
	 * @param jsonString
	 *            A String object containing the a json structure.
	 * @return A JSONObject representing the json structure.
	 * @throws ParseException
	 *             Signals that a parsing exception of some sort has occurred.
	 */
	public static JSONObject parseJSON(String jsonString) throws ParseException
	{
		JSONParser parser = new JSONParser();

		return (JSONObject) parser.parse(jsonString);
	}
}
/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.OpenEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.ScalarQuestion;

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
		return readFile(new FileInputStream(fileName));
	}

	/**
	 * Attempts to read from the specified InputStream object, returning the
	 * content as a String object.
	 * 
	 * @param input
	 *            An InputStream object containing the content of the file.
	 * @return A String object containing the contents of the file.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static String readFile(InputStream input) throws IOException
	{
		BufferedReader reader = null;
		StringBuilder output;
		try
		{
			reader = new BufferedReader(new InputStreamReader(input));
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

	/**
	 * Parses the specified JSONArray and returns a List of Question objects.
	 * 
	 * @param questionListRaw
	 *            A JSON Array containing the raw question list.
	 * @return A List of Question objects.
	 */
	public static List<Question> parseQuestionList(JSONArray questionListRaw)
	{
		List<Question> questionList = new ArrayList<>();

		QuestionType type;
		JSONObject questionRaw;

		for (int index = 0; index < questionListRaw.size(); index++)
		{
			questionRaw = (JSONObject) questionListRaw.get(index);
			type = QuestionType.valueOf((String) questionRaw.get("type"));

			switch (type)
			{
				case MULTIPLE_ANSWER_QUESTION_TYPE:
					questionList.add(new MultipleAnswerQuestion(questionRaw));
					break;
				case MULTIPLE_CHOICE_QUESTION_TYPE:
					questionList.add(new MultipleChoiceQuestion(questionRaw));
					break;
				case OPEN_ENDED_QUESTION_TYPE:
					questionList.add(new OpenEndedQuestion(questionRaw));
					break;
				case SCALAR_QUESTION_TYPE:
					questionList.add(new ScalarQuestion(questionRaw));
					break;
			}
		}

		return questionList;
	}

}
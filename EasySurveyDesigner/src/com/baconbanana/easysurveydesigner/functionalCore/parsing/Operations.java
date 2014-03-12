/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.parsing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.baconbanana.easysurveydesigner.functionalCore.models.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.DateQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.NumericQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.QuestionType;
import com.baconbanana.easysurveydesigner.functionalCore.models.ScalarQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.TextualQuestion;

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
	 * String object representing the separator used in the answers.
	 */
	public static final String SEPARATOR = ";";

	/**
	 * String object representing the filename used to save the json files.
	 */
	public static final String FILENAME = "Survey.json";

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static SimpleDateFormat format = new SimpleDateFormat(
			DATE_FORMAT);
	private final static Pattern p = Pattern.compile(SEPARATOR);
	private final static JSONParser parser = new JSONParser();
	private static OutputStream outputStream;
	private static InputStream inputStream;

	/**
	 * Attempts to read from the specified file, returning the content as a
	 * String object.
	 * 
	 * @return A String object containing the contents of the file.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static String readFile(String filename) throws IOException
	{
		return readFile(new FileInputStream(new File(filename)));
	}

	/**
	 * Attempts to read from the json file, returning the content as a String
	 * object.
	 * 
	 * @param is
	 *            FileInputStream object containing the input stream to be used.
	 * @return A String object containing the contents of the file.
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static String readFile(InputStream is) throws IOException
	{
		byte[] output = new byte[is.available()];

		int totalBytesRead = 0;

		try
		{
			inputStream = new BufferedInputStream(is);

			while (totalBytesRead < output.length)
			{
				int bytesRemaining = output.length - totalBytesRead;
				int bytesRead = inputStream.read(output, totalBytesRead,
						bytesRemaining);
				if (bytesRead > 0)
					totalBytesRead = totalBytesRead + bytesRead;
			}
		}
		finally
		{
			if (inputStream != null)
				inputStream.close();
		}

		return new String(output);
	}

	/**
	 * Attempts to write the specified String object to a file with the
	 * specified filename.
	 * 
	 * @param input
	 *            String object containing the content to be written.
	 * @param filename
	 *            String object representing the filename.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static void writeFile(String input, String filename)
			throws IOException
	{
		writeFile(input, new FileOutputStream(new File(filename)));
	}

	/**
	 * Attempts to write the specified String object to the specified
	 * FileOutputStream object.
	 * 
	 * @param input
	 *            A String object containing the content to be written.
	 * @param fos
	 *            FileOutputStream object containing the stream to be used.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static void writeFile(String input, FileOutputStream fos)
			throws IOException
	{
		outputStream = new BufferedOutputStream(fos);

		try
		{
			outputStream.write(input.getBytes());
		}
		finally
		{
			if (outputStream != null)
				outputStream.close();
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
				case MULTIPLE_ANSWER:
					questionList.add(new MultipleAnswerQuestion(questionRaw));
					break;
				case MULTIPLE_CHOICE:
					questionList.add(new MultipleChoiceQuestion(questionRaw));
					break;
				case CONTINGENCY:
					questionList.add(new ContingencyQuestion(questionRaw));
					break;
				case NUMERIC:
					questionList.add(new NumericQuestion(questionRaw));
					break;
				case DATE:
					questionList.add(new DateQuestion(questionRaw));
					break;
				case TEXTUAL:
					questionList.add(new TextualQuestion(questionRaw));
					break;
				case SCALAR:
					questionList.add(new ScalarQuestion(questionRaw));
					break;
			}
		}

		return questionList;
	}

	/**
	 * Parses the specified string into a java.sql.Date object.
	 * 
	 * @param date
	 *            String Object in the format yyyy-MM-dd representing a date.
	 * @return java.sql.Date object representing the date.
	 * @throws java.text.ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date.
	 */
	public static Date parseDate(String date) throws java.text.ParseException
	{
		format.setLenient(false);

		return new Date(format.parse(date).getTime());
	}

	public static String[] parseAnswers(String answer)
	{
		return (answer.isEmpty()) ? new String[0] : p.split(answer);
	}
}
/**
 * 
 */
package com.baconbanana.easysurveyfunctions.parsing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.baconbanana.easysurveyfunctions.models.ContingencyQuestion;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveyfunctions.models.NumericQuestion;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.QuestionType;
import com.baconbanana.easysurveyfunctions.models.RatingQuestion;
import com.baconbanana.easysurveyfunctions.models.TextualQuestion;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class contains various static methods which assist the other
 *         classes, these range from reading and writing to a file, parsing a
 *         JSON string into a JSONObject and also handling generic array
 *         generation.
 * 
 */
public class Operations
{
	/**
	 * String object representing the separator used in the answers.
	 */
	public static final String SEPARATOR = ";";

	

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static String DATE_HUMAN_READABLE_FORMAT = "dd MMM yyyy";
	private final static SimpleDateFormat format = new SimpleDateFormat(
			DATE_FORMAT);
	private final static SimpleDateFormat humanReadableFormat = new SimpleDateFormat(
			DATE_HUMAN_READABLE_FORMAT);
	private final static Pattern p = Pattern.compile(SEPARATOR);
	private final static JSONParser parser = new JSONParser();
	private static BufferedWriter writer;
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
	public static void writeFile(String filename, String input)
			throws IOException
	{
		writeFile(new FileOutputStream(new File(filename)), input);
	}

	/**
	 * Attempts to write the specified String object to a file with the
	 * specified filename.
	 * 
	 * @param input
	 *            String object containing the content to be written.
	 * @param filename
	 *            Array of String object representing the filename.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	public static void writeFile(String filename, String[] input)
			throws IOException
	{
		writeFile(new FileOutputStream(new File(filename)), input);
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
	public static void writeFile(FileOutputStream fos, String input)
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
	 * Attempts to write the specified String object contained in the array to
	 * the specified FileOutputStream object, adding new line in between them.
	 * 
	 * @param input
	 *            An Array of String object containing the content to be
	 *            written.
	 * @param fos
	 *            FileOutputStream object containing the stream to be used.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred.
	 */
	private static void writeFile(FileOutputStream fos, String[] input)
			throws IOException
	{
		writer = new BufferedWriter(new OutputStreamWriter(fos));

		try
		{
			for (int index = 0; index < input.length; index++)
			{
				if (index > 0)
					writer.newLine();

				writer.write(input[index]);
			}
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
	 * @throws org.json.simple.parser.ParseException 
	 * @throws ParseException
	 *             Signals that a parsing exception of some sort has occurred.
	 */
	@SuppressWarnings("rawtypes")
	public static Map parseJSON(String jsonString) throws ParseException
	{
		try
		{
			return (Map) parser.parse(jsonString);
		}
		catch (org.json.simple.parser.ParseException e)
		{
			throw new ParseException(e.getMessage(), e.getPosition());
		}
	}

	/**
	 * Parses the specified JSONArray and returns a List of Question objects.
	 * 
	 * @param questionListRaw
	 *            A JSON Array containing the raw question list.
	 * @return A List of Question objects.
	 */
	public static List<Question> parseQuestionList(List<Object> questionListRaw)
	{
		List<Question> questionList = new ArrayList<Question>();

		QuestionType type;
		JSONObject questionRaw;

		for (int index = 0; index < questionListRaw.size(); index++)
		{
			questionRaw = (JSONObject) questionListRaw.get(index);

			type = getQuestionType((String) questionRaw.get("type"));

			switch (type)
			{
				case MULTIPLEANSWER:
					questionList.add(new MultipleAnswerQuestion(questionRaw));
					break;
				case MULTIPLECHOICE:
					questionList.add(new MultipleChoiceQuestion(questionRaw));
					break;
				case CONTINGENCY:
					questionList.add(new ContingencyQuestion(questionRaw));
					break;
				case NUMERICAL:
					questionList.add(new NumericQuestion(questionRaw));
					break;
				case DATE:
					questionList.add(new DateQuestion(questionRaw));
					break;
				case TEXTUAL:
					questionList.add(new TextualQuestion(questionRaw));
					break;
				case RATING:
					questionList.add(new RatingQuestion(questionRaw));
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

	/**
	 * Parses the specified human readable string into a java.sql.Date object.
	 * 
	 * @param date
	 *            String Object in the format dd MMM yyyy representing a date.
	 * @return Date object representing the date.
	 * @throws java.text.ParseException
	 *             Signals that an error has been reached unexpectedly while
	 *             parsing the date.
	 */
	public static Date parseHumanReadableDate(String date)
			throws java.text.ParseException
	{
		format.setLenient(true);

		String tmp = date.replaceAll("(?<=\\d)((rd)|(st)|(nd)|(th))\\s(of)\\b",
				"");
		return new Date(humanReadableFormat.parse(tmp).getTime());
	}

	/**
	 * Parses a String object containing the answers to a question, returning a
	 * String array containing answers.
	 * 
	 * @param answer
	 *            Answer to the question.
	 * @return String array containing answers
	 */
	public static String[] parseAnswers(String answer)
	{
		return answer.isEmpty() ? new String[0] : p.split(answer);
	}

	/**
	 * Returns the QuestionType which corresponds to the value specified.
	 * 
	 * @param value
	 *            String object to be parsed, case insensitive.
	 * @return Corresponding Enum object, or null if the parameters are null.
	 */
	public static QuestionType getQuestionType(String value)
	{
		if (value != null)
			return QuestionType.valueOf(value.replaceAll("\\s+", "")
					.toUpperCase());

		return null;
	}
}
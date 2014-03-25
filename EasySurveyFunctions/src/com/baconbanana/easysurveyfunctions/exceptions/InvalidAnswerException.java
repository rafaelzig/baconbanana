/**
 * 
 */
package com.baconbanana.easysurveyfunctions.exceptions;

import java.util.List;

/**
 * Class representing the exceptions thrown whenever an answer given by the user
 * does not exist in the possible choices for the question.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
@SuppressWarnings("serial")
public class InvalidAnswerException extends Exception
{
	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            String object containing the error message.
	 * @param choiceList
	 *            List object containing the possible choices for the question.
	 */
	public InvalidAnswerException(String answer, List<String> choiceList)
	{
		super(prepareMessage(answer, choiceList));
	}

	public InvalidAnswerException(String message)
	{
		super("Invalid answer given:\n" + message);
	}

	/**
	 * Prepares the error message.
	 * 
	 * @param message
	 *            String object containing the error message.
	 * @param choiceList
	 *            List object containing the possible choices for the question.
	 * @return String object representing the error message.
	 */
	private static String prepareMessage(String answer, List<String> choiceList)
	{
		String message = "Answer " + answer
				+ " not present in the possible choices:\n";

		for (String choice : choiceList)
			message += choice + "\n";

		return message;
	}
}
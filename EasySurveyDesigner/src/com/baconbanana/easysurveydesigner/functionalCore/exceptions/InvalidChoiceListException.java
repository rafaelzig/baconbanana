package com.baconbanana.easysurveydesigner.functionalCore.exceptions;

/**
 * Class representing the exceptions thrown whenever a choice list for a
 * question given by the user has less than two choices.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
@SuppressWarnings("serial")
public class InvalidChoiceListException extends Exception
{
	public InvalidChoiceListException(int choiceListSize)
	{
		super(prepareMessage(choiceListSize));
	}

	/**
	 * Prepares the error message to be displayed.
	 * 
	 * @param choiceListSize
	 *            The size of the list containing the choices of the question.
	 * 
	 * @return The error message to be displayed.
	 */
	private static String prepareMessage(int choiceListSize)
	{
		String message = "The list provided contain ";
		message += (choiceListSize < 2) ? "less than two " : "more than ten ";
		message += "alternatives. \n";
		message += "Size = " + choiceListSize;
		return message;
	}
}

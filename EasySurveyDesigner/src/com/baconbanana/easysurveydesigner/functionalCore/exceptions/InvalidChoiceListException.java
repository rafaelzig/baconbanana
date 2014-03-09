package com.baconbanana.easysurveydesigner.functionalCore.exceptions;

/**
 * Class representing the exceptions thrown whenever a choice list for a question given by the user
 * has less than two choices.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
@SuppressWarnings("serial")
public class InvalidChoiceListException extends Exception
{
	public InvalidChoiceListException(int choiceListSize)
	{
		super("The list of choices provided has " + choiceListSize);
	}
}

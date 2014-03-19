package com.baconbanana.easysurveydesigner.functionalCore.exceptions;

/**
 * Exception class which describes when an error occurs when the database
 * resources have not been loaded prior to its utilisation.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
@SuppressWarnings("serial")
public class InvalidStateException extends Exception
{
	public InvalidStateException()
	{
		super("DB Resources have not been loaded !");
	}
}

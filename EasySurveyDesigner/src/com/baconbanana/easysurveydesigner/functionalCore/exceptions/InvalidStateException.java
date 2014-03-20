package com.baconbanana.easysurveydesigner.functionalCore.exceptions;

/**
 * TODO
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

package com.baconbanana.easysurveyfunctions.exceptions;

import com.baconbanana.easysurveyfunctions.models.Question;

/**
 * Class representing the exceptions thrown whenever a subsequent list of
 * questions supplied for a contingency question given by the user contains
 * another contingency question.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
@SuppressWarnings("serial")
public class InvalidSubsequentListException extends Exception
{
	public InvalidSubsequentListException(Question question)
	{
		super(
				"The list of subsequent questions provided contains another contingency question: "
						+ question.getContent());
	}
}

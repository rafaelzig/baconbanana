package com.baconbanana.easysurveyfunctions.models;

import java.util.Map;

/**
 * This class extends the OpenEndedQuestion class and represents a question
 * which has a textual answer.
 * 
 * @author Rafael da Silva Costa & Team
 * 
 */
public class TextualQuestion extends OpenEndedQuestion
{
	/**
	 * String object containing the question's help message to be displayed.
	 */
	private static final String HELP_MESSAGE = "Please fill the answer using the space below:";

	public TextualQuestion(String content, long id)
	{
		super(content, id, HELP_MESSAGE, QuestionType.TEXTUAL);
	}

	@SuppressWarnings("rawtypes")
	public TextualQuestion(Map questionRaw)
	{
		super(questionRaw);
	}
}

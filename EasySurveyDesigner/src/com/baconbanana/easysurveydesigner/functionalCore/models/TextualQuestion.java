package com.baconbanana.easysurveydesigner.functionalCore.models;

import org.json.simple.JSONObject;


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

	public TextualQuestion(String content)
	{
		super(content, HELP_MESSAGE, QuestionType.TEXTUAL);
	}

	public TextualQuestion(JSONObject questionRaw)
	{
		super(questionRaw);
	}
}

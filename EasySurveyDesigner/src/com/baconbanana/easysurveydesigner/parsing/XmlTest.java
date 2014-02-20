/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.baconbanana.easysurveydesigner.functionalCore.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.OpenEndedQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.Question;
import com.baconbanana.easysurveydesigner.functionalCore.Survey;

/**
 * @author Rafael da Silva Costa & Team
 * 
 */
class XmlTest
{
	public static void main(String[] args)
	{
		// creating Questionnaire object
		Survey qOne = new Survey();
		qOne.setName("Introduction");
		qOne.setStage("Initial Consultation");

		// Creating list of questions
		List<Question> questionList = new ArrayList<Question>();
		questionList.add(new OpenEndedQuestion("What is your name?"));
		questionList.add(new OpenEndedQuestion("What is your age?"));
		((OpenEndedQuestion) questionList.get(questionList.size() - 1)).setAnswer("25");
		List<String> choiceList = new ArrayList<>();
		choiceList.add("Yes");
		choiceList.add("No");
		questionList.add(new MultipleChoiceQuestion("Are you a smoker?", choiceList, false));
		choiceList = new ArrayList<>();
		choiceList.add("Male");
		choiceList.add("Female");
		questionList.add(new MultipleChoiceQuestion("What is your gender?", choiceList, false));
		((OpenEndedQuestion) questionList.get(questionList.size() - 1)).setAnswer("Male");
		qOne.setQuestionList(questionList);
		
		try
		{
			File xmlFile = new File("Questionnaire.xml");
			XMLParser.saveToFile(qOne, qOne.getClass(), xmlFile);
			
			Survey qTwo = XMLParser.loadFromFile(Survey.class, new File("Questionnaire.xml"));
			
			// Printing contents of the object from XML
			for (Question q : qTwo.getQuestionList())
			{
				System.out.println(q.getContent());
					if (q instanceof OpenEndedQuestion)
						;// Do Something
					else if (q instanceof MultipleChoiceQuestion)
						System.out.println(((MultipleChoiceQuestion) q).getChoiceList());
					
				System.out.println(q.getClass().getSimpleName() + "\n");
			}
		}
		catch (Exception e)
		{
			// some exception occurred
			e.printStackTrace();
		}
	}
}
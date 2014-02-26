/**
 * 
 */
package com.baconbanana.easysurveydesigner.oldParsing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.baconbanana.easysurveydesigner.oldFunctionalCore.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.oldFunctionalCore.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.oldFunctionalCore.OpenEndedQuestion;
import com.baconbanana.easysurveydesigner.oldFunctionalCore.Question;
import com.baconbanana.easysurveydesigner.oldFunctionalCore.ScalarQuestion;
import com.baconbanana.easysurveydesigner.oldFunctionalCore.Survey;

/**
 * @author Rafael da Silva Costa & Team
 * 
 * A Test !
 */
class XmlTest
{
	public static void main(String[] args)
	{
		// Creating a list of Question objects
		List<Question> questionList = new ArrayList<>();
		
		// Adding an OpenEndedQuestion object
		questionList.add(new OpenEndedQuestion("What is your name?"));
		questionList.add(new OpenEndedQuestion("What is your age?"));
		
		// Creating a list of choices for the next Question object 
		List<String> choiceList = new ArrayList<>();
		choiceList.add("Yes");
		choiceList.add("No");
		
		// Adding a MultipleChoiceQuestion object
		questionList.add(new MultipleChoiceQuestion("Are you a smoker?", choiceList));
		
		// Changing the choices for the next Question object
		choiceList = new ArrayList<>();
		choiceList.add("Male");
		choiceList.add("Female");
		
		// Adding another MultipleChoiceQuestion object
		questionList.add(new MultipleChoiceQuestion("What is your gender?", choiceList));
		
		// Changing the choices for the next Question object
		choiceList = new ArrayList<>();
		choiceList.add("Apple");
		choiceList.add("Orange");
		choiceList.add("Banana");
		choiceList.add("Tangerine");
		choiceList.add("Watermelon");
		choiceList.add("Kiwi");
		choiceList.add("Papaya");
		
		// Adding a MultipleAnswerQuestion object
		questionList.add(new MultipleAnswerQuestion("Select your favourite fruits:", choiceList));		
		
		// Adding ScalarQuestion objects
		questionList.add(new ScalarQuestion("How would you rate Rafael's importance to this project?", ScalarQuestion.IMPORTANCE_SCALE));
		questionList.add(new ScalarQuestion("How would you rate Rafael's performance on this project?", ScalarQuestion.INFLUENCE_SCALE));
		questionList.add(new ScalarQuestion("Rafael is awesome.", ScalarQuestion.AGREEMENT_SCALE));
		
		// Creating a Survey object and setting its list of Question objects
		Survey qOne = new Survey();
		qOne.setName("Introduction");
		qOne.setStage("Initial Consultation"); 
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
					else if (q instanceof ScalarQuestion)
						System.out.println(((ScalarQuestion) q).getChoiceList());
					else if (q instanceof MultipleChoiceQuestion)
						System.out.println(((MultipleChoiceQuestion) q).getChoiceList());
					else if (q instanceof MultipleAnswerQuestion)
						System.out.println(((MultipleAnswerQuestion) q).getChoiceList());
					
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
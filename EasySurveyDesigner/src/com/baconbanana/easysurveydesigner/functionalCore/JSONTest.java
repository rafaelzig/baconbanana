package com.baconbanana.easysurveydesigner.functionalCore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.baconbanana.easysurveydesigner.parsing.Operations;


public class JSONTest
{
	public static void main (String[] args)
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
				Survey qOne = new Survey("Introduction", "Initial Consultation", questionList);
				
				String json = null;
				
				try
				{
					Operations.writeFile(qOne.getJSON().toJSONString(), "Survey.json");
					json = Operations.readFile("Survey.json");
					System.out.println(json);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Survey qTwo = null;
				
				try
				{
					qTwo = new Survey(Operations.parseJSON(json));
				}
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(qTwo.getJSON().toJSONString());
	}
}

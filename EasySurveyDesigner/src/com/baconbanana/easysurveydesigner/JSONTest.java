package com.baconbanana.easysurveydesigner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidChoiceListException;
import com.baconbanana.easysurveydesigner.functionalCore.exceptions.InvalidSubsequentListException;
import com.baconbanana.easysurveydesigner.functionalCore.models.ContingencyQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.DateQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.NumericQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Patient;
import com.baconbanana.easysurveydesigner.functionalCore.models.Question;
import com.baconbanana.easysurveydesigner.functionalCore.models.ScalarQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.models.Survey;
import com.baconbanana.easysurveydesigner.functionalCore.models.TextualQuestion;
import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

public class JSONTest
{
	public static void main(String[] args)
	{
		// Creating a list of Question objects
		List<Question> questionList = new ArrayList<>();

		// Adding an OpenEndedQuestion object
		questionList.add(new TextualQuestion("How are you feeling today?"));
		questionList.add(new DateQuestion(
				"What is the date of your first visit?"));

		// Creating a list of choices for the next Question object
		List<String> choiceList = new ArrayList<>();
		choiceList.add("Yes");
		choiceList.add("No");

		// Creating subsequent questions for the next Question object
		List<Question> subsequentList = new ArrayList<>();
		subsequentList
				.add(new DateQuestion("When have you started smoking?"));
		subsequentList.add(new NumericQuestion(
				"How many packs do you smoke a day?"));

		// Adding a ContingencyQuestion object with subsequent questions
		try
		{
			questionList.add(new ContingencyQuestion("Are you a smoker?",
					choiceList, subsequentList, "Yes"));

			// Changing the choices for the next Question object
			choiceList = new ArrayList<>();
			choiceList.add("Male");
			choiceList.add("Female");

			// Adding another MultipleChoiceQuestion object
			questionList.add(new MultipleChoiceQuestion("What is your gender?",
					choiceList));
		}
		catch (InvalidChoiceListException | InvalidSubsequentListException e)
		{
			e.printStackTrace();
		}

		// Changing the choices for the next Question object
		choiceList = new ArrayList<>();
		choiceList.add("Apple");
		choiceList.add("Orange");
		choiceList.add("Banana");
		choiceList.add("Tangerine");
		choiceList.add("Watermelon");
		choiceList.add("Kiwi");
		choiceList.add("Papaya");

		Patient patient = null;
		
		try
		{
			// Adding a MultipleAnswerQuestion object
			questionList.add(new MultipleAnswerQuestion(
					"Select your favourite fruits:", choiceList));
			// Adding ScalarQuestion objects
			questionList.add(new ScalarQuestion(
					"How would you rate Rafael's importance to this project?",
					ScalarQuestion.IMPORTANCE_SCALE));
			questionList.add(new ScalarQuestion(
					"How would you rate Rafael's performance on this project?",
					ScalarQuestion.INFLUENCE_SCALE));
			questionList.add(new ScalarQuestion("Rafael is awesome.",
					ScalarQuestion.LIKERT_SCALE));
			
			// Initialising the Patient object
			patient = new Patient(1, "John Wayne", "1970-01-01");
		}
		catch (InvalidChoiceListException | java.text.ParseException e)
		{
			e.printStackTrace();
		}

		// Creating a Survey object and setting its list of Question objects
		Survey qOne = new Survey("Introduction", patient,
				"Initial Consultation", questionList);

		String json = null;
		Survey qTwo = null;

		try
		{
			Operations.writeFile(qOne.getJSON().toJSONString(),
					Operations.FILENAME);
			json = Operations.readFile(Operations.FILENAME);
			System.out.println(json);
			
			qTwo = new Survey(Operations.parseJSON(json));
		}
		catch (IOException | java.text.ParseException | ParseException e)
		{
			e.printStackTrace();
		}

		System.out.println(qTwo.getJSON().toJSONString());
	}
}

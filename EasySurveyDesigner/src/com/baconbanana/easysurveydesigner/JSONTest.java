package com.baconbanana.easysurveydesigner;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.baconbanana.easysurveyfunctions.exceptions.InvalidChoiceListException;
import com.baconbanana.easysurveyfunctions.exceptions.InvalidSubsequentListException;
import com.baconbanana.easysurveyfunctions.models.ContingencyQuestion;
import com.baconbanana.easysurveyfunctions.models.DateQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleAnswerQuestion;
import com.baconbanana.easysurveyfunctions.models.MultipleChoiceQuestion;
import com.baconbanana.easysurveyfunctions.models.NumericQuestion;
import com.baconbanana.easysurveyfunctions.models.Patient;
import com.baconbanana.easysurveyfunctions.models.Question;
import com.baconbanana.easysurveyfunctions.models.RatingQuestion;
import com.baconbanana.easysurveyfunctions.models.RatingType;
import com.baconbanana.easysurveyfunctions.models.Survey;
import com.baconbanana.easysurveyfunctions.models.TextualQuestion;
import com.baconbanana.easysurveyfunctions.parsing.Operations;

public class JSONTest
{
	public static void main(String[] args)
	{
		long fakeId = 1;
		// Creating a list of Question objects
		List<Question> questionList = new ArrayList<>();

		// Adding an OpenEndedQuestion object
		questionList.add(new TextualQuestion("How are you feeling today?", fakeId++));
		questionList.add(new DateQuestion(
				"What is the date of your first visit?", fakeId++));

		// Creating a list of choices for the next Question object
		List<String> choiceList = new ArrayList<>();
		choiceList.add("Yes");
		choiceList.add("No");

		// Creating subsequent questions for the next Question object
		List<Question> subsequentList = new ArrayList<>();
		subsequentList
				.add(new DateQuestion("When have you started smoking?", fakeId++));
		subsequentList.add(new NumericQuestion(
				"How many packs do you smoke a day?", fakeId++));

		// Adding a ContingencyQuestion object with subsequent questions
		try
		{
			questionList.add(new ContingencyQuestion("Are you a smoker?", fakeId++,
					choiceList, subsequentList, "Yes"));

			// Changing the choices for the next Question object
			choiceList = new ArrayList<>();
			choiceList.add("Male");
			choiceList.add("Female");

			// Adding another MultipleChoiceQuestion object
			questionList.add(new MultipleChoiceQuestion("What is your gender?", fakeId++,
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
					"Select your favourite fruits:", fakeId++, choiceList));
			// Adding ScalarQuestion objects
			questionList.add(new RatingQuestion(
					"How would you rate Rafael's importance to this project?", fakeId++,
					RatingType.IMPORTANCE_SCALE));
			questionList.add(new RatingQuestion(
					"How would you rate Rafael's performance on this project?", fakeId++,
					RatingType.INFLUENCE_SCALE));
			questionList.add(new RatingQuestion("Rafael is awesome.", fakeId++,
					RatingType.LIKERT_SCALE));
			
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
			Operations.writeFile("Survey.json", qOne.getJSON().toString());
			json = Operations.readFile("Survey.json");
			System.out.println(json);
			
			qTwo = new Survey(Operations.parseJSON(json));
		}
		catch (IOException | ParseException e)
		{
			e.printStackTrace();
		}

		System.out.println(qTwo.getJSON().toString());
	}
}

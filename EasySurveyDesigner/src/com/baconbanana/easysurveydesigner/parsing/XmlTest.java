/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
		questionList.get(questionList.size() - 1).setAnswer("25");
		List<String> choiceList = new ArrayList<>();
		choiceList.add("Yes");
		choiceList.add("No");
		questionList.add(new MultipleChoiceQuestion("Are you a smoker?", choiceList));
		choiceList = new ArrayList<>();
		choiceList.add("Male");
		choiceList.add("Female");
		questionList.add(new MultipleChoiceQuestion("What is your gender?", choiceList));
		questionList.get(questionList.size() - 1).setAnswer("Male");
		qOne.setQuestionList(questionList);

		try
		{
			// create JAXB context and initializing Marshaller
			JAXBContext jc = JAXBContext
					.newInstance(Survey.class);
			Marshaller jm = jc.createMarshaller();

			// for getting nice formatted output
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			// specify the location and name of xml file to be created
			File XMLfile = new File("Questionnaire.xml");

			// Writing to XML file
			jm.marshal(qOne, XMLfile);
			// Writing to console
			jm.marshal(qOne, System.out);
			
			// initializing Unmarshaller and reading from the xml file
			Unmarshaller jun = jc.createUnmarshaller();
			Survey qTwo = (Survey) jun.unmarshal(XMLfile);
			
			// Printing contents of the object from XML
			for (Question q : qTwo.getQuestionList())
			{
				System.out.println(q.getContent());
				switch (q.getType())
				{
					case Question.OPEN_ENDED_QUESTION_TYPE: // Do something
						break;
					case Question.MULTIPLE_CHOICE_QUESTION_TYPE: 
						System.out.println(((MultipleChoiceQuestion) q).getChoiceList());
						break;
				}
				System.out.println(q.getClass().getSimpleName() + "\n");
			}
		}
		catch (JAXBException e)
		{
			// some exception occurred
			e.printStackTrace();
		}
	}
}
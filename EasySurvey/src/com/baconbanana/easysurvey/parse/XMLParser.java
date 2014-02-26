package com.baconbanana.easysurvey.parse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.baconbanana.easysurvey.questtemp.Question;
import com.baconbanana.easysurvey.questtemp.RadioBtnQuestion;
import com.baconbanana.easysurvey.questtemp.TextBoxQuestion;

public class XMLParser {
	
	//Initalises the parser and starts recursive call to get datainfo
	//returns a list of questions
	//COde modified from Google Tutorial
	public ArrayList<Question> parse(InputStreamReader input) throws XmlPullParserException, IOException {
		try{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(input);
			parser.nextTag();
			return readFeed(parser);
		} finally{
			input.close();	
		}
	}
	
	//Finds the list of questionsand add them individually
	private ArrayList<Question> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Question> quest = new ArrayList<Question>();

	    parser.require(XmlPullParser.START_TAG, null, "survey");
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        // Starts by looking for the entry tag
	        if (name.equals("questionList")) {
	        	quest.add(readQuestion(parser));
	        	System.out.print(false);
	        } else {
	            skip(parser);
	        }
	    }  
	    return quest;
	}
	//Used to skip tags that are not important at the current time
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	private Question readQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
	    parser.require(XmlPullParser.START_TAG, null, "questionList");
	    Question quest = null;
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        //parser for text box questions
	        if (name.equals("openEndedQuestion")) {
	        	quest = new TextBoxQuestion();
	        	quest.setQuestionText(readTextBoxQuestionTitle(parser).toString());
	        //parser for  radio button questions
	        } else if (name.equals("multipleChoiceQuestion")) {
	        	quest = new RadioBtnQuestion();
	        	quest.setQuestionText(readMultiChoiceQuestionOptions(parser, quest).toString());
	        } else {
	            skip(parser);
	        }
	    }
	    return quest;
	}

	// Processes title tags in the feed.
	private String readTextBoxQuestionTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, null, "openEndedQuestion");
	    String title = "";
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
		    String name = parser.getName();
		    if(name.equals("answer")){
		    	skip(parser);
		    }else if(name.equals("content")){
		    	title = readText(parser);
		    }
		    //String title = readText(parser);
	    }
	    parser.require(XmlPullParser.END_TAG, null, "openEndedQuestion");
	    return title;
	}
	  
	// Processes link tags in the feed.
	private String readMultiChoiceQuestionOptions(XmlPullParser parser, Question q) throws IOException, XmlPullParserException {
		 parser.require(XmlPullParser.START_TAG, null, "multipleChoiceQuestion");
		    String title = "";
		    while (parser.next() != XmlPullParser.END_TAG) {
		        if (parser.getEventType() != XmlPullParser.START_TAG) {
		            continue;
		        }
			    String name = parser.getName();
			    if(name.equals("answer")){
			    	skip(parser);
			    }else if(name.equals("content")){
			    	title = readText(parser);
			    }else if(name.equals("choices")){
			    	q.setItemOptions(readChoice(parser));
			    }
		    }
		    parser.require(XmlPullParser.END_TAG, null, "multipleChoiceQuestion");
		    return title;
	}

	// Processes summary tags in the feed.
	private ArrayList<String> readChoice(XmlPullParser parser) throws IOException, XmlPullParserException {
	    parser.require(XmlPullParser.START_TAG, null, "choices");
	    ArrayList<String> choices = new ArrayList<String>();
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        if(name.equals("choiceList")){
	        	choices.add(readText(parser));
	        }
	    }
	    parser.require(XmlPullParser.END_TAG, null, "choices");
	    return choices;
	}

	// For the tags title and summary, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
}

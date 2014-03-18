/**
 * 
 */
package com.baconbanana.easysurveydesigner.functionalCore.models;

/**
 * @author Rafael da Silva Costa & Team. An Enumeration representing the
 *         different types of questions.
 */
public enum QuestionType
{
	NUMERIC("Numeric"), DATE("Date"), TEXTUAL("Textual"), MULTIPLE_CHOICE("MultiChoice"), MULTIPLE_ANSWER("MultiAnswer"), SCALAR("Scalar"), CONTINGENCY("Contingency");
	
	private String type;
	//Constructor to assign database values
	private QuestionType(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		String result = "";
		switch(this){
		case NUMERIC :
			result = "Numeric Question";
			break;
		case DATE :
			result = "Date Question";
			break;
		case TEXTUAL : 
			result = "Open Ended Text Question";
			break;
		case MULTIPLE_CHOICE :
			result = "Multiple Choice Question";
			break;
		case MULTIPLE_ANSWER :
			result = "Multiple Answer Question";
			break;
		case SCALAR :
			result = "Rating Question";
			break;
		case CONTINGENCY :
			result = "Contingency Question";
			break;
		}
		return result;
		
	}
}

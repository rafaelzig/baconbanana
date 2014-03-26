package com.baconbanana.easysurveyfunctions.models;

public enum RatingType {
	
	 ACCEPTABILITY_SCALE("acceptable"), LIKERT_SCALE("agree"), APPROPRIATENESS_SCALE("appropriate"),
			AWARENESS_SCALE("aware"), CONCERN_SCALE("concerned"),
			FAMILIARITY_SCALE("familiar"), FREQUENCY_SCALE("frequent"),
			IMPORTANCE_SCALE("important"), INFLUENCE_SCALE("influential"),
			LIKELIHOOD_SCALE("likely"), SATISFACTION_SCALE("satisfied");
	 
	 private String value;
	 private RatingType(String value)
	 {
		 this.value = value;
	 }
	 
	 public String getValue()
	 {
		 return value;
	 }
}

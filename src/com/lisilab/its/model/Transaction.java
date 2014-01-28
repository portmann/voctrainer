package com.lisilab.its.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	Student student;
	Session session;

	long time;
	int duration;

	StepOutcome outcome;

	StudentResponseType studentResponseType;
	TutorResponseType tutorResponseType;

	String problemName;
	String stepName;
	int attemptAtStep;

	String kcCategory;
	String kcSingleKC;
	String kcCategorySingleKC;
	String kcUniqueStep;
	String kcCategoryUniqueStep;

	String levelUnit;
	String levelSection;

	String selection;
	String action;
	String input;
	String feedbackText;

	// Row Sample Name Transaction Id Anon Student Id Session Id Time Time Zone
	// Duration (sec) Student Response Type Student Response Subtype Tutor
	// Response Type Tutor Response Subtype Level (Unit) Level (Section) Problem
	// Name Problem View Problem Start Time Step Name Attempt At Step Outcome
	// Selection Action Input Feedback Text Feedback Classification Help Level
	// Total Num Hints KC (Default)

}

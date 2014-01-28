package com.lisilab.its;

import java.util.Date;

import com.lisilab.its.model.Transaction;

public interface TransactionBuilder {

	// Row,Sample Name,Transaction Id,Anon Student Id,Session
	// Id,Time,Time Zone,Duration (sec),Student Response
	// Type,Student Response Subtype,Tutor Response Type,Tutor
	// Response Subtype,Level (Unit),Level (Section),Problem
	// Name,Problem View,Problem Start Time,Step Name,Attempt At
	// Step,Outcome,Selection,Action,Input,Feedback Text,Feedback
	// Classification,Help Level,Total Num Hints,KC (Default),KC
	// Category (Default),KC (Single-KC),KC Category (Single-KC),KC
	// (Unique-step),KC Category (Unique-step),School,Class

	void setStudent(String name);

	void setTransactionId(String transactionId);

	void setTime(Date time);

	void setDuration(int milliSeconds);

	void setStudentResponseType(String type);

	void setTutorResponseType(String type);

	void setProblemName(String name);

	void setStepName(String name);

	void setOutcome(String outcome);

	void setSchool(String school);

	void setSessionId(String sessionId);

	Transaction getTransaction();

	void newTransaction();

	void setStudentResponseSubType(String value);

	void setInput(String value);

	void setFeedbackText(String value);

	void setFeedbackClassification(String value);

	void setHelpLevel(String value);

	void setTotalNumHints(String value);

	void setKC(String value);

	void setKCCategory(String category);

	void setKCByName(String kcName);

	void setKCSingleKC(String value);

	void setKCCategorySingleKC(String value);

	void setKCUniqueStep(String value);

	void setKCCategoryUniqueStep(String value);

	void setClass(String value);

	void setAction(String value);

}

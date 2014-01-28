package com.lisilab.dictionary;

import java.util.List;
import java.util.Locale;

public interface Dictionary {

	Locale inputLanguage();
	
	Locale outputLanguage();
	
	List<Meaning> meanings(String query);

	List<String> suggestions(String query);

}

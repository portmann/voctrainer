package com.lisilab.dictionary;

import java.net.URL;
import java.util.List;
import java.util.Locale;

public interface Meaning {

	String key();
	
	Locale language();
	
	List<String> synonyms();

	List<String> definitions();

	List<String> exampleSentences();

	List<URL> pictures();

	List<URL> audio();

}

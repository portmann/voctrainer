package com.lisilab.its.dictionary.merriam;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lisilab.dictionary.BasicDictionaryFactory;
import com.lisilab.dictionary.Dictionary;
import com.lisilab.dictionary.DictionaryFactory;
import com.lisilab.dictionary.Meaning;

public class MerriamDictionaryTest {

	private static final Logger log = Logger.getLogger(MerriamDictionaryTest.class
			.getName());
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		factory = new BasicDictionaryFactory();
		dictionary = factory.getDictionary(Locale.ENGLISH, Locale.ENGLISH);
	}

	@After
	public void tearDown() throws Exception {

	}

	private DictionaryFactory factory;
	private Dictionary dictionary;

	@Test
	public void testMeanings() {

		List<Meaning> meanings = dictionary.meanings("dog");
		for(Meaning meaning:meanings){
			log.info(meaning.toString());
		}
		

	}

	@Test
	public void testSuggestions() {

		// TODO
	}

}

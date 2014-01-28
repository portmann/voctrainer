package com.lisilab.dictionary;

import java.util.Locale;

import com.lisilab.dictionary.exception.UnsupportedLanguageException;
import com.lisilab.dictionary.merriam.MerriamDictionary;

public class BasicDictionaryFactory implements DictionaryFactory {

	@Override
	public Dictionary getDictionary(Locale input, Locale output) {
		
		if(input != Locale.ENGLISH || output != Locale.ENGLISH){
			throw new UnsupportedLanguageException();
		}
		
		return new MerriamDictionary();
	}

}

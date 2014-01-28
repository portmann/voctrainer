package com.lisilab.dictionary;

import java.util.Locale;

public interface DictionaryFactory {

	public Dictionary getDictionary(Locale input, Locale output);

}

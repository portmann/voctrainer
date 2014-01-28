package com.lisilab.dictionary;

import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MeaningImpl implements Meaning {

	public MeaningImpl(String key, Locale language, List<String> definitions,
			List<String> synonyms, List<String> exampleSentences,
			List<URL> pictures, List<URL> audio) {
		this.key = key;
		this.language = language;
		this.definitions = definitions;
		this.synonyms = synonyms;
		this.exampleSentences = exampleSentences;
		this.pictures = pictures;
		this.audio = audio;
	}

	private String key;
	private Locale language;
	private List<String> definitions;
	private List<String> synonyms;
	private List<String> exampleSentences;
	private List<URL> pictures;
	private List<URL> audio;

	@Override
	public String key() {
		return key;
	}

	@Override
	public Locale language() {
		return language;
	}

	@Override
	public List<String> synonyms() {
		return synonyms;
	}

	@Override
	public List<String> definitions() {
		return definitions;
	}

	@Override
	public List<String> exampleSentences() {
		return exampleSentences;
	}

	@Override
	public List<URL> pictures() {
		return pictures;
	}

	@Override
	public List<URL> audio() {
		return audio;
	}

	public String toString() {
		String s = "";

		s += "key: " + key + "\n";
		s += "language: " + language.toString() + "\n";

		if (definitions != null) {
			s += "definitions: \n";
			for (String d : definitions) {
				s += " - " + d + "\n";
			}
		}

		if (exampleSentences != null) {
			s += "example sentences: \n";
			for (String e : exampleSentences) {
				s += " - " + e + "\n";
			}
		}

		if (synonyms != null) {
			s += "synonyms: \n";
			for (String sy : synonyms) {
				s += " - " + sy + "\n";
			}
		}

		if (pictures != null) {
			s += "pictures: \n";
			for (URL url : pictures) {
				s += " - " + url + "\n";
			}
		}

		if (audio != null) {
			s += "audio: \n";
			for (URL url : audio) {
				s += " - " + url + "\n";
			}
		}

		return s;
	}
}

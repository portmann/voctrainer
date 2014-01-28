package com.lisilab.dictionary.merriam;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.lisilab.dictionary.Dictionary;
import com.lisilab.dictionary.Meaning;
import com.lisilab.dictionary.MeaningImpl;

public class MerriamDictionary implements Dictionary {

	public MerriamDictionary() {

	}

	private static final Logger log = Logger.getLogger(MerriamDictionary.class
			.getName());

	private XPathFactory xFactory = XPathFactory.newInstance();

	private Document getDocument(String query) {

		Document doc = null;

		try {

			String url = "http://www.dictionaryapi.com/api/v1/references/learners/xml/"
					+ query + "?key=8c74b53e-a08e-4a7e-8b22-c00936b88e6e";

			log.info("url: " + url);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(url);

			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public List<String> synonyms(String query, Locale input, Locale output) {

		List<String> result = new ArrayList<String>();

		// TODO

		return result;
	}

	@Override
	public List<Meaning> meanings(String query) {

		List<Meaning> result = new ArrayList<Meaning>();
		Document doc = getDocument(query);
		XPath xpath = xFactory.newXPath();

		try {

			XPathExpression expr = xpath.compile("//entry");

			NodeList entries = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);

			log.info("entries " + entries.getLength());

			for (int i = 0; i < entries.getLength(); i++) {

				log.info(" ================ entry number: " + i
						+ " ================ ");

				Node entry = entries.item(i);

				List<URL> pictureUrls = getPictureUrls(entry);
				List<URL> audioUrls = getAudioUrls(entry);

				String key = entry.getAttributes().getNamedItem("id")
						.getNodeValue();

				log.info("id: " + key);

				// parse definitions

				NodeList defs = (NodeList) xFactory.newXPath()
						.compile("child::def/dt")
						.evaluate(entry, XPathConstants.NODESET);

				log.info("definitions: " + defs.getLength() + "");

				for (int k = 0; k < defs.getLength(); k++) {

					Node definitionNode = defs.item(k);

					if (definitionNode != null) {

						result.add(parseDefDtNode(definitionNode, key,
								pictureUrls, audioUrls));
					}
				}

				// parse phrases

				NodeList phraseNodes = (NodeList) xFactory.newXPath()
						.compile("child::dro")
						.evaluate(entry, XPathConstants.NODESET);

				for (int k = 0; k < defs.getLength(); k++) {

					Node phrase = phraseNodes.item(k);

					if (phrase != null) {

						Node keyNode = ((Node) xFactory.newXPath()
								.compile("child::dre/text()")
								.evaluate(phrase, XPathConstants.NODE));

						Node defNode = ((Node) xFactory.newXPath()
								.compile("child::def/dt")
								.evaluate(phrase, XPathConstants.NODE));

						if (defNode != null && keyNode != null
								&& keyNode != null) {
							String newKey = keyNode.getNodeValue();

							log.info("phrase: " + newKey);

							if (newKey != null && !newKey.equals("")) {
								Meaning meaning = parseDefDtNode(defNode,
										newKey, pictureUrls, audioUrls);
								if (meaning != null) {
									result.add(meaning);
								}
							}
						}
					}
				}
				log.info("=================================\n=======================================");
			}
			log.info("length: " + entries.getLength());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Meaning parseDefDtNode(Node definitionEntry, String key,
			List<URL> pictureUrls, List<URL> audioUrls) {

		if (definitionEntry == null) {
			throw new IllegalArgumentException();
		}

		// log.info("base uri: " + definitionEntry.getBaseURI());
		// log.info("local name: " + definitionEntry.getLocalName());
		// log.info("node name: " + definitionEntry.getNodeName());
		// log.info("node type: " + definitionEntry.getNodeType());
		// log.info("bide value: " + definitionEntry.getNodeValue());

		Meaning result = null;

		List<String> definitions = new ArrayList<String>();

		try {

			List<Node> definitionNodes = new ArrayList<Node>();

			Node definitionNode = ((Node) xFactory.newXPath()
					.compile("child::text()")
					.evaluate(definitionEntry, XPathConstants.NODE));

			if (definitionNode != null) {
				definitionNodes.add(definitionNode);
			}

			definitionNode = ((Node) xFactory.newXPath().compile("child::un")
					.evaluate(definitionEntry, XPathConstants.NODE));
			if (definitionNode != null) {

				Node node = ((Node) xFactory.newXPath()
						.compile("child::text()")
						.evaluate(definitionEntry, XPathConstants.NODE));
				if (node != null) {
					definitionNodes.add(node);
				}
			}

			for (Node node : definitionNodes) {

				String d = node.getNodeValue();
				if (d != null && !d.equals("") && !d.equals(":")) {
					log.info("defintion: " + d);
					d = d.replace(":", "");
					definitions.add(d);
				}
			}

			List<String> exampleSentences = new ArrayList<String>();

			// get example sentences
			NodeList exampleSentenceNodes = (NodeList) xFactory.newXPath()
					.compile("child::vi")
					.evaluate(definitionEntry, XPathConstants.NODESET);

			log.info("num exmample sentences: "
					+ exampleSentenceNodes.getLength());

			for (int s = 0; s < exampleSentenceNodes.getLength(); s++) {
				Node exampleSentenceNode = exampleSentenceNodes.item(s);
				String exampleSentence = exampleSentenceNode.getTextContent();
				if (exampleSentence != null) {
					log.info("example sentence: " + exampleSentence);
					exampleSentences.add(exampleSentence);
				}
			}

			result = new MeaningImpl(key, Locale.ENGLISH, definitions, null,
					exampleSentences, pictureUrls, audioUrls);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		log.info("---------------------------------\n---------------------------------------");
		return result;
	}

	private List<URL> getPictureUrls(Node entryNode) {
		// image and sound api docs:
		// http://www.dictionaryapi.com/info/faq-audio-image.htm

		// pictures

		List<URL> pictures = new ArrayList<URL>();

		NodeList pictureNodes;
		try {
			pictureNodes = (NodeList) xFactory.newXPath()
					.compile("child::art/artref")
					.evaluate(entryNode, XPathConstants.NODESET);

			for (int s = 0; s < pictureNodes.getLength(); s++) {

				Node pictureNode = pictureNodes.item(0).getAttributes()
						.getNamedItem("id");

				if (pictureNode != null) {
					String pictureUrl = pictureNode.getNodeValue();

					if (pictureUrl != null && !pictureUrl.equals("")) {

						pictureUrl = pictureUrl.replace(".eps", ".gif");
						pictureUrl = pictureUrl.replace(".tif", ".gif");

						try {
							pictures.add(new URL(
									"http://www.learnersdictionary.com/art/ld/"
											+ pictureUrl));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}

			}

		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		return pictures;
	}

	private List<URL> getAudioUrls(Node entryNode) {
		List<URL> audio = new ArrayList<URL>();

		try {
			NodeList audioNodes = (NodeList) xFactory.newXPath()
					.compile("child::sound/wav")
					.evaluate(entryNode, XPathConstants.NODESET);

			log.info("num of audio nodes: " + audioNodes.getLength());

			for (int s = 0; s < audioNodes.getLength(); s++) {

				if (audioNodes.item(s) != null) {
					Node audioNode = audioNodes.item(s);

					Node audioUrlNode = (Node) xFactory.newXPath()
							.compile("child::text()")
							.evaluate(audioNode, XPathConstants.NODE);

					if (audioUrlNode != null) {
						String audioUrl = audioUrlNode.getNodeValue();
						if (audioUrl != null && !audioUrl.equals("")) {

							/*
							 * Start with the base URL:
							 * http://media.merriam-webster.com/soundc11/ Add
							 * the first letter of the wav file as a
							 * subdirectory ("h" in the example above).
							 */

							String subdirectory = audioUrl.charAt(0) + "";

							/*
							 * If the file name begins with "bix", the
							 * subdirectory should be "bix". If the file name
							 * begins with "gg", the subdirectory should be
							 * "gg". If the file name begins with a number, the
							 * subdirectory should be "number".
							 */

							if (audioUrl.startsWith("bix")) {
								subdirectory = "bix";
							} else if (audioUrl.startsWith("gg")) {
								subdirectory = "gg";
							} else if (Character.isDigit(audioUrl.charAt(0))) {
								subdirectory = "number";
							}

							audioUrl = audioUrl.replace(".eps", ".gif");
							audioUrl = audioUrl.replace(".tif", ".gif");

							try {
								audio.add(new URL(
										"http://media.merriam-webster.com/soundc11/"
												+ subdirectory + "/" + audioUrl));
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

			}
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return audio;
	}

	@Override
	public List<String> suggestions(String query) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
		
		return null;
	}

	@Override
	public Locale inputLanguage() {
		return Locale.ENGLISH;
	}

	@Override
	public Locale outputLanguage() {
		return Locale.ENGLISH;
	}

}
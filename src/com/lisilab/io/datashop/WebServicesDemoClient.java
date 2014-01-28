package com.lisilab.io.datashop;

/*
 * Carnegie Mellon University, Human Computer Interaction Institute
 * Copyright 2009
 * All Rights Reserved
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demo Client for Datashop Web Services. This example will get transaction
 * data, then count the number of observations for each problem hierarchy,
 * problem, step, outcome and input tuple.
 */

// WebServicesDemoClient
public final class WebServicesDemoClient extends DatashopClient {
	/** Root of the URL to access web services on QA server. */
	public static final String QA_SERVICES_ROOT = "https://pslc-qa.andrew.cmu.edu/datashop";
	/** Root of the URL to access web services on production server. */
	public static final String PROD_SERVICES_ROOT = "https://pslcdatashop.web.cmu.edu";

	/** Datasets path. */
	private static final String DATASETS_PATH = "/datasets/";

	/** Transactions path. */
	private static final String TXS_PATH = "/transactions"
			+ "?headers=false&limit=5000"
			+ "&cols=problem_hierarchy,problem_name,step_name,outcome,input";

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            root of the URL
	 * @param apiToken
	 *            public API token for authentication
	 * @param secret
	 *            secret key for authentication
	 */
	private WebServicesDemoClient(String root, String apiToken, String secret) {
		super(root, apiToken, secret);
	};

	/**
	 * Return a tree map of the results of the web service call.
	 * 
	 * @param datasetId
	 *            the dataset id as a string
	 * @return a map of the results
	 */
	public TreeMap<TransactionDataSubset, Integer> runReport(String datasetId) {
		String path = DATASETS_PATH + datasetId + TXS_PATH;
		System.out.println(path);
		HttpURLConnection conn = serviceGetConnection(path);
		conn.setRequestProperty("accept", "text/xml");
		TreeMap<TransactionDataSubset, Integer> map = new TreeMap();
		try {
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String row = null;
			while ((row = reader.readLine()) != null) {
				TransactionDataSubset t = TransactionDataSubset
						.createTransaction(row);
				if (t != null) {
					Integer value = map.get(t);
					if (value == null) {
						map.put(t, new Integer(1));
					} else {
						map.put(t, ++value);
					}
				}
			}
		} catch (IOException ioe) {
			try {
				print(conn.getErrorStream(), System.err);
			} catch (IOException anotherIOE) {
				System.out.println("IOException occurred.");
				anotherIOE.printStackTrace();
			}
		}
		return map;
	}

	private String accessKeyId = "FEWT8XESFVKJCZ4HW99C";
	private String secretAccessKeyId = "PHRpEzBZ3KJk3UrGw4imWOUyndjsu5QPAYBPAICq";

	/**
	 * Creates an instance of this class given the root URL to make web service
	 * calls to. This method will also load authentication properties from a
	 * file in the current directory.
	 * 
	 * @param rootURL
	 *            the root URL
	 * @return an instance of this class
	 */
	public static WebServicesDemoClient createErrorReport(String rootURL) {
		String apiToken = null;
		String secret = null;
		loadProperties();
		if (apiToken == null) {
			apiToken = System.getProperty("api.token");
		}
		if (secret == null) {
			secret = System.getProperty("secret");
		}
		if (apiToken == null) {
			System.out.println("No property or parameter found for api.token");
			System.exit(1);
		}
		if (secret == null) {
			System.out.println("No property or parameter found for secret");
			System.exit(1);
		}
		WebServicesDemoClient errorReport = new WebServicesDemoClient(rootURL,
				apiToken, secret);
		return errorReport;
	}

	/**
	 * Look for a file named "webservices.properties" in the current directory,
	 * and load the authentication properties (api.token and secret) from it.
	 */
	private static void loadProperties() {
		String userDir = System.getProperty("user.dir");
		File propsFile = new File(userDir + "/webservices.properties");

		if (propsFile.exists()) {
			try {
				System.getProperties().load(new FileReader(propsFile));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * Simple method to print the map to standard out.
	 * 
	 * @param map
	 *            the map produced by the runReport method
	 */
	public void printMap(Map<TransactionDataSubset, Integer> map) {
		for (Map.Entry<TransactionDataSubset, Integer> entry : map.entrySet()) {
			System.out.println("key is " + entry.getKey() + " and value is "
					+ entry.getValue());
		}
	}

	/**
	 * Run this client from the command line.
	 * 
	 * @param args
	 *            Arguments are alternating keys and values. Valid keys are:
	 *            dataset - the dataset id indicating which dataset to run
	 *            report on
	 */
	public static void main(String[] args) {
		
		loadProperties();
		
		String datasetId = null;
		try {
			
			if (datasetId == null) {
				datasetId = System.getProperty("datasetId");
			}

			if (datasetId == null) {
				System.out
						.println("No property or parameter found for datasetId");
				System.exit(1);
			}

			WebServicesDemoClient client = createErrorReport(QA_SERVICES_ROOT);
			TreeMap<TransactionDataSubset, Integer> map = client
					.runReport(datasetId);
			client.printMap(map);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
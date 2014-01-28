package com.lisilab.dictionary.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DictionaryServlet extends HttpServlet {

	private static final long serialVersionUID = -1770004600184497901L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		System.out.println("path info " + req.getPathInfo());

		String pathInfo = req.getPathInfo();

		String[] functionAndQuery = pathInfo.split("/");

		String function = functionAndQuery[0];
		String query = functionAndQuery[1];
		
		Locale input = Locale.forLanguageTag(req.getParameter("input"));
		Locale output = Locale.forLanguageTag(req.getParameter("output"));
		
		
		System.out.println("function: " + function);
		System.out.println("query: " + query);
		System.out.println("input: " + input);
		System.out.println("output: " + output);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

}
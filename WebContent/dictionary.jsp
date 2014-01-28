

<%@page import="com.lisilab.dictionary.BasicDictionaryFactory"%>
<%@page import="com.lisilab.dictionary.Dictionary"%>
<%@page import="com.lisilab.dictionary.Meaning"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URL;"%>

<%@ include file="logincheck.jsp"%>

<jsp:include page="header.jsp" />

<h1>Dictionary</h1>

<form action="Dictionary.jsp" method="post">
	<input type="text" name="query"> <br /> <input type="submit"
		value="Translate">
</form>

<%
	String query = request.getParameter("query");
	if (query != null) {
		Dictionary dict = new BasicDictionaryFactory().getDictionary(
				Locale.ENGLISH, Locale.ENGLISH);

		List<Meaning> meanings = dict.meanings(query);

		if (meanings == null || meanings.size() == 0) {
			List<String> suggestions = dict.suggestions(query);
		} else {

			for (Meaning meaning : meanings) {

				out.println("<h3>" + meaning.key() + "</h3>");

				for (URL pictureUrl : meaning.pictures()) {
					out.println("<a href='" + pictureUrl
							+ "'>picture</a>");
				}

				for (URL audioUrl : meaning.audio()) {
					out.println("<a href='" + audioUrl + "'>audio</a>");
				}

				if (meaning.definitions() != null
						&& meaning.definitions().size() > 0) {
					out.println("<h4>Definitions</h4>");

					out.println("<ul>");
					for (String def : meaning.definitions()) {
						out.println("<li>" + def + "</li>");
					}
					out.println("</ul>");
				}

				if (meaning.exampleSentences() != null
						&& meaning.exampleSentences().size() > 0) {
					out.println("<h4>Examples</h4>");
					out.println("<ul>");
					for (String example : meaning.exampleSentences()) {
						out.println("<li>" + example + "</li>");
					}
					out.println("</ul>");
				}
			}
		}
	}
%>

<jsp:include page="footer.jsp" />

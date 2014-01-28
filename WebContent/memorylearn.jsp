<html>
<head>
<title>LISILab.com</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<%
	int userID = Integer.parseInt(request.getParameter("userID"));
	int wordID = Integer.parseInt(request.getParameter("wordID"));
	String userInput = request.getParameter("user_input");

	if (wordID != 0) {
		//check user input
		out.print("Feedback - input was ture or false.");
	}
%>
</head>
<body>
	<h1>Memory Learn Interface</h1>
	<%
		//get next word to learn
		wordID = 1;
		String definition = "Thats a definition to learn.";
	%>
	<br />
	<%=definition%>
	<br />

	<form action="memoryLearn.jsp" method="GET">
		Solution: <input type="text" name="user_input"> <input
			type="hidden" name="userID" value=<%=userID%>> <input
			type="hidden" name="wordID" value=<%=wordID%>> <br /> <input
			type="submit" value="Next" /> <br /> <br />

	</form>


</body>
</body>
</html>
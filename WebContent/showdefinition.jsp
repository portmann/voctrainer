<html>
<head>
<title>LISILab.com</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<%
int userID = Integer.parseInt(request.getParameter("userID"));
String lookup_word = request.getParameter("lookup_word");
String definition ="Standart definition.";

//get definition


%>
</head>
<body>
	<h1><%=lookup_word%></h1>

	<%=definition%>

</body>
</body>
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logout</title>
</head>
<body>
	<%
		session.removeAttribute("userId");
		session.invalidate();
	%>
	<h1>You are logged out.</h1>

</body>
</html>
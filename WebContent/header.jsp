<%@ page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>

<meta name="description" content="<fmt:message key='metadescription'/>" />
<meta name="keywords" content="Learning Innovation Lab" />
<meta name="author" content="LISILab">
<meta charset="UTF-8" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="css/app.css" />

<title>LisiLab</title>

<%
	String userName = (String) session.getAttribute("userName");
%>

</head>
<body>
	<div id="headerContainer">
		<span>Welcome <%=userName%></span> <br /> <a href='Logout.jsp'>Logout</a>
	</div>

	<div id="contentContainer">
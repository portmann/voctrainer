<%@page import="com.lisilab.UserUtils"%>
<%@page import="com.lisilab.its.model.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Login Page</h1>
	<h2>Signup Details</h2>

	<a href="Signup.jsp">Signup</a>

	<%
		String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (username != null && password != null) {

		UserUtils utils = new UserUtils();
		Student user = utils.loadUser(username);

		if (user == null) {
			// there is no such user
			out.println("<span><strong>There is no user named "
					+ username + "</strong></span>");
		} else if (user.getPasswordHash() == password.hashCode()) {

			// logged in
			session.setAttribute("userName", username);
			response.sendRedirect("Dictionary.jsp");
		} else {
			out.println("<span><strong>Wrong Password, try again.</strong></span>");
		}
			}
	%>

	<form action="Login.jsp" method="post">
		<br />Username:<input type="text" name="username"> <br />Password:<input
			type="password" name="password"> <br /> <input type="submit"
			value="Submit">
	</form>
</body>
</html>

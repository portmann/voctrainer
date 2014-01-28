<%@page import="com.lisilab.UserUtils"%>
<%@page import="com.lisilab.its.model.Student"%>

<%@ page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>

<meta charset="UTF-8" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />

<title>LisiLab - Sign Up</title>

</head>
<body>
	<h1>Sign Up Page</h1>
	<a href="Login.jsp">Login</a>
	<%
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username != null && password != null) {

			UserUtils userUtils = new UserUtils();
			Student user = userUtils.loadUser(username);

			if (user == null) {

		// create and save new user
		user = new Student();
		user.setPasswordHash(password.hashCode());
		user.setUserName(username);
		userUtils.storeUser(user);

		// redirect to login page
		response.sendRedirect("Login.jsp");

			} else {

		// there is already an user with this name
		out.println("<span><strong>there is already an user with this name, try again with another name.</strong></span>");
			}
		}
	%>

	<form action="Signup.jsp" method="post">
		<br />Username:<input type="text" name="username"> <br />Password:<input
			type="password" name="password"> <br /> <input type="submit"
			value="Submit">
	</form>
</body>
</html>
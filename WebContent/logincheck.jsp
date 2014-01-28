
<%
	String userName = (String) session.getAttribute("userName");
	if (userName == null) {
		response.sendRedirect("Login.jsp");
		return;
	}
%>
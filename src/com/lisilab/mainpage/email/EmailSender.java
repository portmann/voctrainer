package com.lisilab.mainpage.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailSender extends HttpServlet {

	private static final long serialVersionUID = 5120127623774143294L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");

		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = name + "\n" + subject + "\n" + email + "\n" + message;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email,
					name));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"info@LISILab.com", "Your name"));
			msg.setSubject("FeedbackForm - LISILAB.com");
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (Exception e) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Something went wrong. Please try again.");
			throw new RuntimeException(e);
		}

		resp.setContentType("text/plain");
		resp.getWriter().println(
				"Thank you for your feedback. An Email has been sent out.");
	}
}

package com.amazon.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("email")
public class EmailService {

	private final JavaMailSender sender;
	@Value("${app.mail.from}")
	private String fromMsg;
	
	public EmailService(JavaMailSender sender) {
		this.sender=sender;
	}

	public void sentEmail(String to, String subject, String body) throws MessagingException {

		if (to==null || subject==null || body==null)
	        throw new IllegalArgumentException("Invalid Details");

	    MimeMessage message=sender.createMimeMessage();
	    MimeMessageHelper helper=new MimeMessageHelper(message,true);
	    helper.setFrom(fromMsg);
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText(body);
	    sender.send(message);
	}
}

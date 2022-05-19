package com.manisha.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender mailsender;

	public boolean SendEmail(String to, String subject, String body) {
		boolean isSent = false;
		try {
			MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailsender.send(mimeMessage);

			isSent = true;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return isSent;

	}

}

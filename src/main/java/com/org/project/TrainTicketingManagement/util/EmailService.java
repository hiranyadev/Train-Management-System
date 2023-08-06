package com.org.project.TrainTicketingManagement.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.org.project.TrainTicketingManagement.dto.EmailMessage;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("expotrain@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");

    }

	public void sendSimpleEmail(EmailMessage ems) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("expotrain@gmail.com");
        message.setTo(ems.getToMail());
        message.setText(ems.getBody());
        message.setSubject(ems.getSubject());
        mailSender.send(message);
        System.out.println("Mail Send...");
		
	}

	public void emailReceipt(EmailMessage ems) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();		
		try {
		    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		    helper.setFrom("expotrain@gmail.com");
		    helper.setTo(ems.getToMail());
		    helper.setText(ems.getBody());
		    helper.setSubject(ems.getSubject());
		    helper.addAttachment("pdf", ems.getAttachment());
		    mailSender.send(mimeMessage);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

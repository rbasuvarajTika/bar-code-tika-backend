package com.tika.barcode.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentName, byte[] attachmentData) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);

        // Add attachment
        messageHelper.addAttachment(attachmentName, new ByteArrayDataSource(attachmentData, "application/pdf"));

        javaMailSender.send(mimeMessage);
    }
    

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text);

        // Add attachment
       // messageHelper.addAttachment(attachmentName, new ByteArrayDataSource(attachmentData, "application/pdf"));

        javaMailSender.send(mimeMessage);
    }
}

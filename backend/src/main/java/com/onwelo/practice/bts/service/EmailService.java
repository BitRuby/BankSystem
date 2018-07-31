package com.onwelo.practice.bts.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(CsvService.class);
    private static String BankEmail = "onwelo-bts@outlook.com";

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(BankEmail);
            helper.setReplyTo(BankEmail);

            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            Logger.debug(e.getMessage(), e);
        }

        mailSender.send(message);
    }

}

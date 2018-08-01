package com.onwelo.practice.bts.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Configuration
@PropertySource("classpath:application.properties")
public class EmailService {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(CsvService.class);

    @Value("${spring.mail.username}")
    private String bankEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(bankEmail);
            helper.setReplyTo(bankEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            Logger.debug(e.getMessage(), e);
        }

        mailSender.send(message);
    }

}

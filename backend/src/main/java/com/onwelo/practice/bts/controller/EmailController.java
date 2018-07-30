package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class EmailController {
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private String subject, content, to;

    @Autowired
    public EmailController(EmailService emailService, TemplateEngine templateEngine) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    @RequestMapping("/api/email")
    public void sendEmail() {
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("description", content);
        
        String body = templateEngine.process("emailTemplate", context);
        emailService.sendEmail(to, subject, body);
    }
}

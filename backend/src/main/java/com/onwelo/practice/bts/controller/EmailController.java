package com.onwelo.practice.bts.controller;

import com.onwelo.practice.bts.service.EmailService;
import com.onwelo.practice.bts.utils.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class EmailController {
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailController(EmailService emailService, TemplateEngine templateEngine) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
    }

    @RequestMapping(value = "/api/email", method = RequestMethod.POST)
    public EmailConfig sendEmail(@RequestBody EmailConfig emailConfig) {
        Context context = new Context();
        context.setVariable("subject", emailConfig.getSubject());
        context.setVariable("accountNo", emailConfig.getAccountNo());
        context.setVariable("title", emailConfig.getTitle());
        context.setVariable("value", emailConfig.getValue());
        context.setVariable("transferDate", emailConfig.getTransferDate());
        context.setVariable("reason", emailConfig.getReason());
        
        String body = templateEngine.process("emailTemplate", context);
        emailService.sendEmail(emailConfig.getTo(), emailConfig.getSubject(), body);
        return emailConfig;
    }
}

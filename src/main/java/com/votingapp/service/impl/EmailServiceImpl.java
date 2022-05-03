package com.votingapp.service.impl;

import com.votingapp.enumeration.EmailLayout;
import com.votingapp.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Log
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void send(String to, String subject, String message) {
        try {
            emailSender.send(buildSimpleMailMessage(to, subject, message));

        } catch (Exception e) {
            log.warning("Error sending email");
        }
    }

    @Override
    @Async
    public void send(String to, String subject, EmailLayout layout, Object body) {

        String message;
        try {
            message = buildHtml(layout, body);

            log.info("Sending email");

            emailSender.send(buildMimeMessage(to, subject, message));
        } catch (Exception e) {
            log.warning("Error sending email");
        }
    }

    @Override
    public SimpleMailMessage buildSimpleMailMessage(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        return simpleMailMessage;
    }

    @Override
    public MimeMessage buildMimeMessage(String to, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setText(message, true);

        return mimeMessage;
    }

    @Override
    public String buildHtml(EmailLayout layout, Object object) {
        Context context = new Context();
        context.setVariable("object", object);

        return templateEngine.process(String.format("%s/%s", "email", layout.getTemplateName()), context);
    }
}

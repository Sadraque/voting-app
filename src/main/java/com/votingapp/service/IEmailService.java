package com.votingapp.service;

import com.votingapp.enumeration.EmailLayout;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface IEmailService {

    void send(final String to, final String subject, final String message);

    void send(final String to, final String subject, final EmailLayout layout, final Object body);

    SimpleMailMessage buildSimpleMailMessage(final String to, final String subject, final String message);

    MimeMessage buildMimeMessage(final String to, final String subject, final String message) throws MessagingException;

    String buildHtml(final EmailLayout layout, final Object object);
}

package com.votingapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class BeanConfig {

    private final EmailConfig emailConfig;

    public BeanConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", emailConfig.getProtocol());
        props.put("mail.smtp.auth", emailConfig.getAuth());
        props.put("mail.smtp.starttls.enable", emailConfig.getTls());
        props.put("mail.debug", emailConfig.getDebug());

        return mailSender;
    }
}

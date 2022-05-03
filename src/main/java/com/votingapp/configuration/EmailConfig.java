package com.votingapp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.smtp.auth}")
    private Boolean auth;

    @Value("${spring.mail.properties.smtp.starttls.enabled}")
    private Boolean tls;

    @Value("${spring.mail.debug}")
    private Boolean debug;
}

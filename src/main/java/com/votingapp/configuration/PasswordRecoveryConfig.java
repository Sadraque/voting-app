package com.votingapp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PasswordRecoveryConfig {

    @Value("${password.recovery.expirationInHour}")
    private Integer expirationInHour;

}

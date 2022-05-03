package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordRecoveryRequestException extends RuntimeException {
    public PasswordRecoveryRequestException(String message) {
        super(message);
    }

    public PasswordRecoveryRequestException() {
        super("Invalid password recovery solicitation");
    }
}

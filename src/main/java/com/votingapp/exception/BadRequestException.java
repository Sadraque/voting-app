package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad request");
    }

    public BadRequestException(String cause) {
        super(String.format("Bad request. %s", cause));
    }
}

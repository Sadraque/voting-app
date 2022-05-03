package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WebClientRequestException extends RuntimeException {
    public WebClientRequestException(String message) {
        super(message);
    }

    public WebClientRequestException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }

    public WebClientRequestException() {

    }
}

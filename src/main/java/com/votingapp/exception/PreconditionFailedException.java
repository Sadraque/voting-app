package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailedException extends RuntimeException {
    public PreconditionFailedException() {
        super("Precondition failed!");
    }

    public PreconditionFailedException(String cause) {
        super(String.format("Precondition failed. %s", cause));
    }
}

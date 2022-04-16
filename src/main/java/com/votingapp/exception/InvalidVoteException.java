package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidVoteException extends RuntimeException {
    public InvalidVoteException() {
        super("Invalid vote");
    }

    public InvalidVoteException(String cause) {
        super(String.format("Invalid vote. %s", cause));
    }
}

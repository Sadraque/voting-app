package com.votingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entity not found");
    }

    public EntityNotFoundException(Class clazz) {
        super(String.format("%s not found", clazz.getSimpleName()));
    }

    public EntityNotFoundException(Class clazz, String cause) {
        super(String.format("%s not found. %s", clazz.getSimpleName(), cause));
    }
}

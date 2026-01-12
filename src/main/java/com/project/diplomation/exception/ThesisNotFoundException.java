package com.project.diplomation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ThesisNotFoundException extends RuntimeException {
    public ThesisNotFoundException(String message) {
        super(message);
    }
}

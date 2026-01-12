package com.project.diplomation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UniversityTutorNotFoundException extends RuntimeException {
    public UniversityTutorNotFoundException(String message) {
        super(message);
    }
}

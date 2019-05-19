package com.rabobank.recordsprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFilePathException extends RuntimeException {

    public InvalidFilePathException(String message) {
        super(message);
    }
}

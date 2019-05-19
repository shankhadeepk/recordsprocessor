package com.rabobank.recordsprocessor.model;

import org.springframework.http.HttpStatus;

public class AppError {

    private HttpStatus status;
    private String errorMessage;

    public AppError(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

}

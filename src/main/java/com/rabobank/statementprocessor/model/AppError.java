package com.rabobank.statementprocessor.model;

import org.springframework.http.HttpStatus;

/*
 * AppError return as response for any error, to the client.
 * @Author Shankhadeep Karmakar
 * @Version 1.0
 *
 *
 * */
public class AppError {

    private HttpStatus status;
    private String errorMessage;

    public AppError(HttpStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

}

package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handle(NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(40401, new Date(), ex.getMessage()));
    }
}

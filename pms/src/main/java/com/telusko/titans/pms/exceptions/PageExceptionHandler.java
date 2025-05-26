package com.telusko.titans.pms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class PageExceptionHandler {

    @ExceptionHandler(InvalidPageException.class)
    public ResponseEntity<ErrorResponse> handlePageException(InvalidPageException e){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

    }
}

package com.devco.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {
  @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
  public ResponseEntity<Void> validationExceptionHandler(Exception exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}

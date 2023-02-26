package com.example.test_kte_labs.exceptions.exception_handler;

import com.example.test_kte_labs.exceptions.ClientIsNotFoundException;
import com.example.test_kte_labs.exceptions.ProductIsNotFoundException;
import com.example.test_kte_labs.exceptions.ReceivedTotalDoesNotMatchCalculatedTotalException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ClientIsNotFoundException.class)
    public ResponseEntity<Object> handleUserIsNotFoundException(ClientIsNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ProductIsNotFoundException.class)
    public ResponseEntity<Object> handleProductIsNotFoundException(ProductIsNotFoundException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ReceivedTotalDoesNotMatchCalculatedTotalException.class)
    public ResponseEntity<Object> handleReceivedTotalDoesNotMatchCalculatedTotalException
            (ReceivedTotalDoesNotMatchCalculatedTotalException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

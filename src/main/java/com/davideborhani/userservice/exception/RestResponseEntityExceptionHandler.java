package com.davideborhani.userservice.exception;

import com.davideborhani.userservice.exception.invaliduser.InvalidUserException;
import com.davideborhani.userservice.exception.notfounduser.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value
            = { InvalidUserException.class })
    protected ResponseEntity<Object> handleInvalidUserConflict(
            RuntimeException ex, WebRequest request) {
        log.info("Exception " + ex.getMessage() + " - HttpStatus : " + HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(value
            = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundUserConflict(
            RuntimeException ex, WebRequest request) {
        log.info("Exception " + ex.getMessage() + " - HttpStatus : " + HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
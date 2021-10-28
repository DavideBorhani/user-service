package com.davideborhani.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}

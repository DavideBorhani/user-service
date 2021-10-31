package com.davideborhani.userservice.exception.invaliduser;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }
}

package com.davideborhani.userservice.exception;

import lombok.Getter;

@Getter
public class NotFoundUserException extends RuntimeException {

    public NotFoundUserException(String message) {
        super(message);
    }
}

package com.davideborhani.userservice.exception.invaliduser;

public class BirthDateEmptyException extends InvalidUserException{
    public BirthDateEmptyException(String message) {
        super(message);
    }
}

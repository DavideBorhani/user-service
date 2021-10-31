package com.davideborhani.userservice.exception.invaliduser;

public class BirthDateNotAdultException extends InvalidUserException{
    public BirthDateNotAdultException(String message) {
        super(message);
    }
}

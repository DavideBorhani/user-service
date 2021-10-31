package com.davideborhani.userservice.exception.invaliduser;

public class UsernameEmptyException extends InvalidUserException{
    public UsernameEmptyException(String message) {
        super(message);
    }
}

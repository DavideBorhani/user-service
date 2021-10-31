package com.davideborhani.userservice.exception.invaliduser;

public class UsernameAlreadyRegisteredException extends InvalidUserException{
    public UsernameAlreadyRegisteredException(String message) {
        super(message);
    }
}

package com.davideborhani.userservice.exception.invaliduser;

public class CountryOfResidenceEmptyException extends InvalidUserException{
    public CountryOfResidenceEmptyException(String message) {
        super(message);
    }
}

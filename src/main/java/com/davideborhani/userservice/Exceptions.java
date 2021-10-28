package com.davideborhani.userservice;

public enum Exceptions {

    USER_EMPTY("User can't be null or empty"),
    USERNAME_EMPTY("userName can't be null or empty"),
    BIRTH_DATE_EMPTY("birthDate can't be null or empty"),
    BIRTH_DATE_INVALID("birthDate must be in the format dd/MM/yyyy"),
    COUNTRY_OF_RESIDENCE_EMPTY("countryOfResidence can't be null or empty"),
    COUNTRY_OF_RESIDENCE_NOT_FR("countryOfResidence must be France"),
    PHONE_NUMBER_INVALID("Invalid phone number"),
    GENDER_INVALID("Gender can be only male or female");

    private String message;

    Exceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }



}

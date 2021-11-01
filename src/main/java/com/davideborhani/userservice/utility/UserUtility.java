package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.enums.Exceptions;
import com.davideborhani.userservice.exception.invaliduser.*;
import com.davideborhani.userservice.model.dto.UserDtoRequest;
import com.davideborhani.userservice.model.dto.UserDtoResponse;
import com.davideborhani.userservice.model.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class UserUtility {

    private UserUtility() {
    }

    public static User userCorrectnessCheck(UserDtoRequest userDtoRequest) {

        Date dateToCheck;

        checkUserName(userDtoRequest);
        dateToCheck = checkBirthDate(userDtoRequest);
        checkCountryOfResidence(userDtoRequest);
        checkPhoneNumber(userDtoRequest);
        checkGender(userDtoRequest);

        User user = fromUserDtoRequestToUserEntity(userDtoRequest, dateToCheck);

        return user;
    }

    private static void checkGender(UserDtoRequest userDtoRequest) {
        if (userDtoRequest.getGender() != null && !userDtoRequest.getGender().isEmpty()) {
            if (!userDtoRequest.getGender().equalsIgnoreCase("male") &&
                    !userDtoRequest.getGender().equalsIgnoreCase("female")
            ) {
                throw new GenderInvalidException(Exceptions.GENDER_INVALID.getMessage());
            }
            userDtoRequest.setGender(userDtoRequest.getGender().toLowerCase());
        }
    }

    private static void checkPhoneNumber(UserDtoRequest userDtoRequest) {
        /*
            \d{10} matches 1234567890
            (?:\d{3}-){2}\d{4} matches 123-456-7890
            \(\d{3}\)\d{3}-?\d{4} matches (123)456-7890 or (123)4567890
        */
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (userDtoRequest.getPhoneNumber() != null &&
                !userDtoRequest.getPhoneNumber().isEmpty()) {
            if (!Pattern.compile(pattern).matcher(userDtoRequest.getPhoneNumber()).matches()) {
                throw new PhoneNumberInvalidException(Exceptions.PHONE_NUMBER_INVALID.getMessage());
            }
            String phoneNumber = userDtoRequest.getPhoneNumber().replaceAll("[()-]", "");
            userDtoRequest.setPhoneNumber(phoneNumber);
        }
    }

    private static void checkCountryOfResidence(UserDtoRequest userDtoRequest) {
        if (userDtoRequest.getCountryOfResidence() == null || userDtoRequest.getCountryOfResidence().isEmpty()) {
            throw new CountryOfResidenceEmptyException(Exceptions.COUNTRY_OF_RESIDENCE_EMPTY.getMessage());
        } else {
            if (!userDtoRequest.getCountryOfResidence().equalsIgnoreCase("france")) {
                throw new CountryOfResidenceNotFrException(Exceptions.COUNTRY_OF_RESIDENCE_NOT_FR.getMessage());
            }
            userDtoRequest.setCountryOfResidence(userDtoRequest.getCountryOfResidence().toLowerCase());
        }
    }

    private static Date checkBirthDate(UserDtoRequest userDtoRequest) {
        Date dateToCheck;
        if (userDtoRequest.getBirthDate() == null || userDtoRequest.getBirthDate().isEmpty()) {
            throw new BirthDateEmptyException(Exceptions.BIRTH_DATE_EMPTY.getMessage());
        } else {

            try {
                dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(userDtoRequest.getBirthDate());
            } catch (ParseException e) {
                throw new BirthDateInvalidException(Exceptions.BIRTH_DATE_INVALID.getMessage());
            }
            int years = Period.between(
                    dateToCheck.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    LocalDate.now()
            ).getYears();
            if(years<18){
                throw new BirthDateNotAdultException(Exceptions.BIRTH_DATE_NOT_ADULT.getMessage());
            }
        }
        return dateToCheck;
    }

    private static void checkUserName(UserDtoRequest userDtoRequest) {
        if (userDtoRequest.getUsername() == null || userDtoRequest.getUsername().isEmpty()) {
            throw new UsernameEmptyException(Exceptions.USERNAME_EMPTY.getMessage());
        }
    }

    public static UserDtoResponse fromUserEntitytoUserDtoResponse(User userEntity){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthDate = dateFormat.format(userEntity.getBirthDate());
        return UserDtoResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .birthDate(birthDate)
                .countryOfResidence(userEntity.getCountryOfResidence())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .build();
    }

    private static User fromUserDtoRequestToUserEntity(UserDtoRequest userDtoRequest, Date dateToCheck) {
        User user = new User();
        user.setUsername(userDtoRequest.getUsername());
        user.setBirthDate(dateToCheck);
        user.setCountryOfResidence(userDtoRequest.getCountryOfResidence());
        user.setPhoneNumber(userDtoRequest.getPhoneNumber());
        user.setGender(userDtoRequest.getGender());
        return user;
    }

}

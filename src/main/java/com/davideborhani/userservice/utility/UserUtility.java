package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.exception.InvalidUserException;
import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.model.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class UserUtility {

    private UserUtility() {
    }

    public static User userCorrectnessCheck(UserDto userDto) {
        Date dateToCheck;

        if (userDto == null) {
            throw new InvalidUserException(Exceptions.USER_EMPTY.getMessage());
        }
        if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            throw new InvalidUserException(Exceptions.USERNAME_EMPTY.getMessage());
        }
        if (userDto.getBirthDate() == null || userDto.getBirthDate().isEmpty()) {
            throw new InvalidUserException(Exceptions.BIRTH_DATE_EMPTY.getMessage());
        } else {

            try {
                dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(userDto.getBirthDate());
            } catch (ParseException e) {
                throw new InvalidUserException(Exceptions.BIRTH_DATE_INVALID.getMessage());
            }
        }
        if (userDto.getCountryOfResidence() == null || userDto.getCountryOfResidence().isEmpty()) {
            throw new InvalidUserException(Exceptions.COUNTRY_OF_RESIDENCE_EMPTY.getMessage());
        } else {
            if (!userDto.getCountryOfResidence().equalsIgnoreCase("france")) {
                throw new InvalidUserException(Exceptions.COUNTRY_OF_RESIDENCE_NOT_FR.getMessage());
            }
            userDto.setCountryOfResidence(userDto.getCountryOfResidence().toLowerCase());
        }
        /*
            \d{10} matches 1234567890
            (?:\d{3}-){2}\d{4} matches 123-456-7890
            \(\d{3}\)\d{3}-?\d{4} matches (123)456-7890 or (123)4567890
        */
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (userDto.getPhoneNumber() != null &&
                !userDto.getPhoneNumber().isEmpty()) {
            if (!Pattern.compile(pattern).matcher(userDto.getPhoneNumber()).matches()) {
                throw new InvalidUserException(Exceptions.PHONE_NUMBER_INVALID.getMessage());
            }
            String phoneNumber = userDto.getPhoneNumber().replaceAll("[()-]", "");
            userDto.setPhoneNumber(phoneNumber);
        }
        if (userDto.getGender() != null && !userDto.getGender().isEmpty()) {
            if (!userDto.getGender().equalsIgnoreCase("male") &&
                    !userDto.getGender().equalsIgnoreCase("female")
            ) {
                throw new InvalidUserException(Exceptions.GENDER_INVALID.getMessage());
            }
            userDto.setGender(userDto.getGender().toLowerCase());

        }
        User user = new User();
        user.setUsername(userDto.getUserName());
        user.setBirthDate(dateToCheck);
        user.setCountryOfResidence(userDto.getCountryOfResidence());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setGender(userDto.getGender());
        return user;
    }

    public static UserDto fromUserEntitytoUserDto(User userEntity){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthDate = dateFormat.format(userEntity.getBirthDate());
        return UserDto.builder()
                .userName(userEntity.getUsername())
                .birthDate(birthDate)
                .countryOfResidence(userEntity.getCountryOfResidence())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .build();
    }
}

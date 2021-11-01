package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.enums.Exceptions;
import com.davideborhani.userservice.exception.invaliduser.*;
import com.davideborhani.userservice.model.dto.UserDto;
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

    public static User userCorrectnessCheck(UserDto userDto) {

        Date dateToCheck;

        checkUserName(userDto);
        dateToCheck = checkBirthDate(userDto);
        checkCountryOfResidence(userDto);
        checkPhoneNumber(userDto);
        checkGender(userDto);

        User user = fromUserDtoToUserEntity(userDto, dateToCheck);

        return user;
    }

    private static void checkGender(UserDto userDto) {
        if (userDto.getGender() != null && !userDto.getGender().isEmpty()) {
            if (!userDto.getGender().equalsIgnoreCase("male") &&
                    !userDto.getGender().equalsIgnoreCase("female")
            ) {
                throw new GenderInvalidException(Exceptions.GENDER_INVALID.getMessage());
            }
            userDto.setGender(userDto.getGender().toLowerCase());
        }
    }

    private static void checkPhoneNumber(UserDto userDto) {
        /*
            \d{10} matches 1234567890
            (?:\d{3}-){2}\d{4} matches 123-456-7890
            \(\d{3}\)\d{3}-?\d{4} matches (123)456-7890 or (123)4567890
        */
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (userDto.getPhoneNumber() != null &&
                !userDto.getPhoneNumber().isEmpty()) {
            if (!Pattern.compile(pattern).matcher(userDto.getPhoneNumber()).matches()) {
                throw new PhoneNumberInvalidException(Exceptions.PHONE_NUMBER_INVALID.getMessage());
            }
            String phoneNumber = userDto.getPhoneNumber().replaceAll("[()-]", "");
            userDto.setPhoneNumber(phoneNumber);
        }
    }

    private static void checkCountryOfResidence(UserDto userDto) {
        if (userDto.getCountryOfResidence() == null || userDto.getCountryOfResidence().isEmpty()) {
            throw new CountryOfResidenceEmptyException(Exceptions.COUNTRY_OF_RESIDENCE_EMPTY.getMessage());
        } else {
            if (!userDto.getCountryOfResidence().equalsIgnoreCase("france")) {
                throw new CountryOfResidenceNotFrException(Exceptions.COUNTRY_OF_RESIDENCE_NOT_FR.getMessage());
            }
            userDto.setCountryOfResidence(userDto.getCountryOfResidence().toLowerCase());
        }
    }

    private static Date checkBirthDate(UserDto userDto) {
        Date dateToCheck;
        if (userDto.getBirthDate() == null || userDto.getBirthDate().isEmpty()) {
            throw new BirthDateEmptyException(Exceptions.BIRTH_DATE_EMPTY.getMessage());
        } else {

            try {
                dateToCheck = new SimpleDateFormat("dd/MM/yyyy").parse(userDto.getBirthDate());
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

    private static void checkUserName(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new UsernameEmptyException(Exceptions.USERNAME_EMPTY.getMessage());
        }
    }

    public static UserDto fromUserEntitytoUserDto(User userEntity){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String birthDate = dateFormat.format(userEntity.getBirthDate());
        return UserDto.builder()
                .username(userEntity.getUsername())
                .birthDate(birthDate)
                .countryOfResidence(userEntity.getCountryOfResidence())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .build();
    }

    private static User fromUserDtoToUserEntity(UserDto userDto, Date dateToCheck) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setBirthDate(dateToCheck);
        user.setCountryOfResidence(userDto.getCountryOfResidence());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setGender(userDto.getGender());
        return user;
    }

}

package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.exception.invaliduser.*;
import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.model.entity.User;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserUtilityTest {
    
    private static final String pattern = "dd/MM/yyyy";
    private static final String validBirthDate = "31/12/2000";
    private static final String validUsername = "username";
    private static final String validCountryOfResidence = "frAnce";
    private static final String validPhoneNumber = "1234567890";
    private static final String validGender = "malE";

    @Test
    void userCorrectnessCheck() throws ParseException {
        //testing -> private static void checkUserName(UserDto userDto)
        UserDto userDto = UserDto.builder().build();
        assertThrows(UsernameEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setUserName("");
        assertThrows(UsernameEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setUserName(validUsername);
        //testing -> private static Date checkBirthDate(UserDto userDto)
        assertThrows(BirthDateEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setBirthDate("31-12-2121");
        assertThrows(BirthDateInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setBirthDate("31/12/2121");
        assertThrows(BirthDateNotAdultException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setBirthDate(validBirthDate);
        //testing -> private static void checkCountryOfResidence(UserDto userDto)
        assertThrows(CountryOfResidenceEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setCountryOfResidence("fra");
        assertThrows(CountryOfResidenceNotFrException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setCountryOfResidence(validCountryOfResidence);
        //testing -> private static void checkPhoneNumber(UserDto userDto)
        userDto.setPhoneNumber("123456789");
        assertThrows(PhoneNumberInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setPhoneNumber(validPhoneNumber);
        //testing -> private static void checkGender(UserDto userDto)
        userDto.setGender("zzz");
        assertThrows(GenderInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDto));
        userDto.setGender(validGender);
        //testing -> the returned object User
        Date expectedDate;
        expectedDate = new SimpleDateFormat(pattern).parse(userDto.getBirthDate());
        User expected = new User();
        expected.setUsername(userDto.getUserName());
        expected.setBirthDate(expectedDate);
        expected.setCountryOfResidence(userDto.getCountryOfResidence());
        expected.setPhoneNumber(userDto.getPhoneNumber());
        expected.setGender(userDto.getGender().toLowerCase());
        assertEquals(expected, UserUtility.userCorrectnessCheck(userDto));
    }

    @Test
    void fromUserEntitytoUserDto() throws ParseException {

        Date date;
        date = new SimpleDateFormat(pattern).parse(validBirthDate);

        User userEntity = User.builder()
                .username(validUsername)
                .birthDate(date)
                .countryOfResidence(validCountryOfResidence.toLowerCase())
                .phoneNumber(validPhoneNumber)
                .gender(validGender.toLowerCase())
                .build();

        UserDto expected = UserDto.builder()
                .userName(userEntity.getUsername())
                .birthDate(validBirthDate)
                .countryOfResidence(userEntity.getCountryOfResidence())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .build();

        assertEquals(expected, UserUtility.fromUserEntitytoUserDto(userEntity));
    }
}
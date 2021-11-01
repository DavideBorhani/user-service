package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.exception.invaliduser.*;
import com.davideborhani.userservice.model.dto.UserDtoRequest;
import com.davideborhani.userservice.model.dto.UserDtoResponse;
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
        //testing -> private static void checkUserName(UserDtoRequest userDtoRequest)
        UserDtoRequest userDtoRequest = UserDtoRequest.builder().build();
        assertThrows(UsernameEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setUsername("");
        assertThrows(UsernameEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setUsername(validUsername);
        //testing -> private static Date checkBirthDate(UserDtoRequest userDtoRequest)
        assertThrows(BirthDateEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setBirthDate("31-12-2121");
        assertThrows(BirthDateInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setBirthDate("31/12/2121");
        assertThrows(BirthDateNotAdultException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setBirthDate(validBirthDate);
        //testing -> private static void checkCountryOfResidence(UserDtoRequest userDtoRequest)
        assertThrows(CountryOfResidenceEmptyException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setCountryOfResidence("fra");
        assertThrows(CountryOfResidenceNotFrException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setCountryOfResidence(validCountryOfResidence);
        //testing -> private static void checkPhoneNumber(UserDtoRequest userDtoRequest)
        userDtoRequest.setPhoneNumber("123456789");
        assertThrows(PhoneNumberInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setPhoneNumber(validPhoneNumber);
        //testing -> private static void checkGender(UserDtoRequest userDtoRequest)
        userDtoRequest.setGender("zzz");
        assertThrows(GenderInvalidException.class, () -> UserUtility.userCorrectnessCheck(userDtoRequest));
        userDtoRequest.setGender(validGender);
        //testing -> the returned object User
        Date expectedDate;
        expectedDate = new SimpleDateFormat(pattern).parse(userDtoRequest.getBirthDate());
        User expected = new User();
        expected.setUsername(userDtoRequest.getUsername());
        expected.setBirthDate(expectedDate);
        expected.setCountryOfResidence(userDtoRequest.getCountryOfResidence());
        expected.setPhoneNumber(userDtoRequest.getPhoneNumber());
        expected.setGender(userDtoRequest.getGender().toLowerCase());
        assertEquals(expected, UserUtility.userCorrectnessCheck(userDtoRequest));
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

        UserDtoResponse expected = UserDtoResponse.builder()
                .username(userEntity.getUsername())
                .birthDate(validBirthDate)
                .countryOfResidence(userEntity.getCountryOfResidence())
                .phoneNumber(userEntity.getPhoneNumber())
                .gender(userEntity.getGender())
                .build();

        assertEquals(expected, UserUtility.fromUserEntitytoUserDtoResponse(userEntity));
    }
}
package com.davideborhani.userservice.utility;

import com.davideborhani.userservice.model.dto.UserDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class UserUtility {

    private UserUtility() {
    }

    public static void userCorrectnessCheck(UserDto userDto) {
        if (userDto == null) {
            //lancia eccezione
        }
        if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            //lancia eccezione
        }
        if (userDto.getBirthDate() == null) {
            //lancia eccezione
        } else {

            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(userDto.getBirthDate());
            } catch (ParseException e) {
                //lancia eccezione
                e.printStackTrace();
            }
        }
        if (userDto.getCountryOfResidence() == null || userDto.getCountryOfResidence().isEmpty()) {
            //lancia eccezione
        } else {
            if (!userDto.getCountryOfResidence().equalsIgnoreCase("france")) {
                //lancia eccezione
            }
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
                //lancia eccezione
            }
        }
        if(userDto.getGender()!=null && !userDto.getGender().isEmpty()){
            if(!userDto.getGender().equalsIgnoreCase("male") ||
                    !userDto.getGender().equalsIgnoreCase("female")
            ){
                //lancia eccezione
            }
        }
    }
}

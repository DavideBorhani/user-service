package com.davideborhani.userservice.service;

import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.utility.UserUtility;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {

    public UserDto insertUser(UserDto userDto){
        UserUtility.userCorrectnessCheck(userDto);
        return userDto;
    }

}

package com.davideborhani.userservice.service;

import com.davideborhani.userservice.enums.Exceptions;
import com.davideborhani.userservice.exception.InvalidUserException;
import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.model.dto.UserIdDto;
import com.davideborhani.userservice.model.entity.User;
import com.davideborhani.userservice.repository.UserRepository;
import com.davideborhani.userservice.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public UserIdDto insertUser(UserDto userDto){
        User user = UserUtility.userCorrectnessCheck(userDto);
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new InvalidUserException(Exceptions.USERNAME_ALREADY_REGISTERED.getMessage());
        }
        user = userRepository.save(user);
        return new UserIdDto(user.getId().toString());
    }

    public UserDto getUser(String userId){
        User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        return UserUtility.fromUserEntitytoUserDto(user);
    }

}

package com.davideborhani.userservice.service;

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
        User user = userRepository.save(UserUtility.userCorrectnessCheck(userDto));
        return new UserIdDto(user.getId().toString());
    }

    public User getUser(String userId){
        return userRepository.findById(Long.valueOf(userId)).orElse(null);
    }

}

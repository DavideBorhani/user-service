package com.davideborhani.userservice.service;

import com.davideborhani.userservice.enums.Exceptions;
import com.davideborhani.userservice.exception.invaliduser.UsernameAlreadyRegisteredException;
import com.davideborhani.userservice.exception.notfounduser.UserNotFoundException;
import com.davideborhani.userservice.model.dto.UserDtoRequest;
import com.davideborhani.userservice.model.dto.UserDtoResponse;
import com.davideborhani.userservice.model.entity.User;
import com.davideborhani.userservice.repository.UserRepository;
import com.davideborhani.userservice.utility.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public UserDtoResponse insertUser(UserDtoRequest userDtoRequest){
        User user = UserUtility.userCorrectnessCheck(userDtoRequest);
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new UsernameAlreadyRegisteredException(Exceptions.USERNAME_ALREADY_REGISTERED.getMessage());
        }
        user = userRepository.save(user);
        return UserUtility.fromUserEntitytoUserDtoResponse(user);
    }

    public UserDtoResponse getUser(String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException(Exceptions.USER_NOT_FOUND.getMessage()));
        return UserUtility.fromUserEntitytoUserDtoResponse(user);
    }

    public UserDtoResponse updateUser(String userId, UserDtoRequest userDtoRequest) {
        User user = UserUtility.userCorrectnessCheck(userDtoRequest);
        User userToUpdate = userRepository.findByIdAndUsername(Long.valueOf(userId), user.getUsername())
                .orElseThrow(() -> new UserNotFoundException(Exceptions.USER_NOT_FOUND.getMessage()));
        user.setId(userToUpdate.getId());
        user = userRepository.save(user);
        return UserUtility.fromUserEntitytoUserDtoResponse(user);
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException(Exceptions.USER_NOT_FOUND.getMessage()));
        userRepository.delete(user);
    }
}

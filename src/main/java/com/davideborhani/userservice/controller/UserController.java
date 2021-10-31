package com.davideborhani.userservice.controller;

import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.model.dto.UserIdDto;
import com.davideborhani.userservice.model.entity.User;
import com.davideborhani.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
            public UserIdDto insertUser(@RequestBody UserDto userDto) {
        return userService.insertUser(userDto);
    }
}

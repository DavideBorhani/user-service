package com.davideborhani.userservice.controller;

import com.davideborhani.userservice.model.dto.UserDto;
import com.davideborhani.userservice.model.dto.UserIdDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable("userId") String userId){
        return new UserDto();
    }
    @PostMapping
    public UserIdDto insertUser(@RequestBody UserDto userDto){
        return new UserIdDto(userDto.getUserName()+userDto.getBirthDate());
    }
}

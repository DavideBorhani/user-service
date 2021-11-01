package com.davideborhani.userservice.controller;

import com.davideborhani.userservice.model.dto.UserDtoRequest;
import com.davideborhani.userservice.model.dto.UserDtoResponse;
import com.davideborhani.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Retrieve a User from a userId",
    notes = "In order to use this api you have to: provide a valid userId to find a specific User already inserted in the in memory database",
    response = UserDtoResponse.class)
    public UserDtoResponse getUser(
            @ApiParam(value = "userId value of the User you need to retrieve", required = true)
            @PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Insert a User",
            notes = "In order to use this api you have to: provide a valid UserDtoRequest to create a new User in the in memory database",
            response = UserDtoResponse.class)
    public UserDtoResponse insertUser(
            @ApiParam(value = "UserDtoRequest that represent the User you need to insert", required = true)
            @RequestBody UserDtoRequest userDtoRequest) {
        return userService.insertUser(userDtoRequest);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a User",
            notes = "In order to use this api you have to: provide a valid UserDtoRequest to update the User retrieved by userId and username(you can't update the username field after creation of the User) in the in memory database",
            response = UserDtoResponse.class)
    public UserDtoResponse updateUser(
            @ApiParam(value = "userId value of the User you need to update", required = true)
            @PathVariable("userId") String userId,
            @ApiParam(value = "UserDtoRequest that represent the new data of the User you need to update", required = true)
            @RequestBody UserDtoRequest userDtoRequest){
        return userService.updateUser(userId, userDtoRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a User from a userId",
            notes = "In order to use this api you have to: provide a valid userId to delete a specific User already inserted in the in memory database")
    public void deleteUser(
            @ApiParam(value = "userId value of the User you need to delete", required = true)
            @PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }
}

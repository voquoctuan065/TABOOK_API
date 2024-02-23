package com.tacm.tabooksapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tacm.tabooksapi.domain.dto.UserDto;
import com.tacm.tabooksapi.domain.entities.UserEntity;
import com.tacm.tabooksapi.mapper.Mapper;
import com.tacm.tabooksapi.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users/register")
    public UserDto createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity addedUser = userService.addUser(userEntity);
        return userMapper.mapTo(addedUser);
    }

    @GetMapping(path = "/users/login")
    public ResponseEntity<UserDto> userLogin(
        @RequestBody UserDto userDto
    ) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        Optional<UserEntity> foundedUser = userService.login(userEntity);
        return foundedUser.map(
            user -> ResponseEntity.ok(userMapper.mapTo(user))
        ).orElse(
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        );
    } 
}

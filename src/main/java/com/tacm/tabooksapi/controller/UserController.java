package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ReqRes;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Users> getUserProfileByJwt(@RequestHeader("Authorization") String token) throws UserException {
        Users users = userService.findUserProfileByJwt(token);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

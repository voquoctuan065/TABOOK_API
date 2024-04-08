package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.config.JwtProvider;
import com.tacm.tabooksapi.domain.dto.LoginRequest;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.repository.UserRepository;
import com.tacm.tabooksapi.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tacm.tabooksapi.domain.dto.ReqRes;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userServiceImpl;
    @Autowired
    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder
    ,UserServiceImpl userServiceImpl) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
    }
    @PostMapping("/signup")
    public ResponseEntity<ReqRes> creatUserHandler(@RequestBody Users users) throws  UserException{
        String email = users.getEmail();
        String password = users.getPassword();
        String full_name = users.getFull_name();
        Users isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist != null) {
            throw new UserException("Email have already used with another account");
        }

        Users createdUser = new Users();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFull_name(full_name);


        Users savedUser = userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generatToken(authentication);

        ReqRes authResponse = new ReqRes(token, "Signup success!");
        return new ResponseEntity<ReqRes>(authResponse, HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<ReqRes> loginHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generatToken(authentication);
        ReqRes authResponse = new ReqRes(token, "SignIn Success!");
        return new ResponseEntity<ReqRes>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

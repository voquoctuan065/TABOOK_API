package com.tacm.tabooksapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.tacm.tabooksapi.domain.entities.UserEntity;
import com.tacm.tabooksapi.repository.UserRepository;
import com.tacm.tabooksapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        if(userEntity.getUser_fullname() == null ||
            userEntity.getEmail() == null || userEntity.getPassword() == null
        ) {
            throw new IllegalArgumentException("Missing required fields");
        }
        String hashedPassword = passwordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(hashedPassword);
        userEntity.setRole("CLIENT");

        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> login(UserEntity userEntity) {
        UserEntity userEntity2 = userRepository.findByEmail(userEntity.getEmail()); 
        if(userEntity != null) {
            String password = userEntity.getPassword();
            String encodedPassword = userEntity2.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if(isPwdRight) {
                return userRepository.findOneByEmailAndPassword(userEntity.getEmail(), encodedPassword);
            }
        }
        return null;
    }
    
}

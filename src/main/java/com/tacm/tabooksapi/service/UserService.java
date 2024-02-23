package com.tacm.tabooksapi.service;

import java.util.Optional;

import com.tacm.tabooksapi.domain.entities.UserEntity;

public interface UserService {

    UserEntity addUser(UserEntity userEntity);
    Optional<UserEntity> login(UserEntity userEntity);
}

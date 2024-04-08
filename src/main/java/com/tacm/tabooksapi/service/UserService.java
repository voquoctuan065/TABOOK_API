package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.UserException;
import jdk.jshell.spi.ExecutionControl;

public interface UserService {
    Users findUserById(Long userId) throws UserException;

    Users findUserProfileByJwt(String jwt) throws UserException;
}

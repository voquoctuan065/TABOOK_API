package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.config.JwtProvider;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.repository.UserRepository;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl (UserRepository userRepository,
                            JwtProvider jwtProvider,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with email" + username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(),authorities);
    }

    @Override
    public Users findUserById(Long userId) throws UserException {
        Optional<Users> user = userRepository.findById(userId);

        if(user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id: " + userId);
    }

    @Override
    public Users findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        Users user = userRepository.findByEmail(email);
        if (user == null) {
           throw new UserException("User not found with email " + email);
        }
        return user;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserException {
        Users users = userRepository.findByEmail(email);

        if(users != null) {
            users.setResetPasswordToken(token);
            userRepository.save(users);
        }
        else {
            throw new UserException("User not found with email " + email);
        }
    }

    @Override
    public Users get(String resetPasswordToken) throws UserException {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public void updatePassword(Users users, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);

        users.setPassword(encodePassword);
        users.setResetPasswordToken(null);

        userRepository.save(users);
    }
}

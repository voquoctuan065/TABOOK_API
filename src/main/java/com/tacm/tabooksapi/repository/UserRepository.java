package com.tacm.tabooksapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacm.tabooksapi.domain.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}

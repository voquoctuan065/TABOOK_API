package com.tacm.tabooksapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacm.tabooksapi.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Optional<UserEntity> findOneByEmailAndPassword(String email, String password);
}

package com.tacm.tabooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tacm.tabooksapi.domain.entities.Authors;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {
    
}

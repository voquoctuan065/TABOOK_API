package com.tacm.tabooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacm.tabooksapi.domain.entities.Catagories;

public interface CatagoriesRepository extends JpaRepository<Catagories, Long> {
    
}

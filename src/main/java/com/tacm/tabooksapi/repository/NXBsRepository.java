package com.tacm.tabooksapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tacm.tabooksapi.domain.entities.NXBs;

@Repository
public interface NXBsRepository extends JpaRepository<NXBs, Long> {
    
}

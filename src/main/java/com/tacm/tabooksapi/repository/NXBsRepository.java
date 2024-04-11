package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.NXBs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NXBsRepository extends JpaRepository<NXBs, Long> {
}

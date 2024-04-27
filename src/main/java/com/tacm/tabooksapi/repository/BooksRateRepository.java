package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.BooksRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRateRepository extends JpaRepository<BooksRate, Long> {
}

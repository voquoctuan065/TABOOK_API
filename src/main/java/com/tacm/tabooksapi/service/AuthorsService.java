package com.tacm.tabooksapi.service;

import java.util.List;
import java.util.Optional;

import com.tacm.tabooksapi.domain.entities.Authors;

public interface AuthorsService {

    Authors createAuthors(Authors authors);

    List<Authors> findAll();

    Optional<Authors> findOne(Long id);

    boolean isExist(Long id);

    Authors partialUpdate(Long id, Authors authors);

    void delete(Long id);

    
}

package com.tacm.tabooksapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tacm.tabooksapi.domain.entities.Authors;
import com.tacm.tabooksapi.repository.AuthorsRepository;
import com.tacm.tabooksapi.service.AuthorsService;

@Service
public class AuthorsServiceImpl implements AuthorsService {

    @Autowired
    private AuthorsRepository authorsRepository;

    @Override
    public Authors createAuthors(Authors authors) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        authors.setCreated_at(currentDateTime);
        return authorsRepository.save(authors);
    }

    @Override
    public List<Authors> findAll() {
        return StreamSupport.stream(authorsRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }

    @Override
    public Optional<Authors> findOne(Long id) {
        return authorsRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return authorsRepository.existsById(id);
    }

    @Override
    public Authors partialUpdate(Long id, Authors authors) {
        authors.setAuthor_id(id);
        LocalDateTime updatedCurrentTime = LocalDateTime.now();

        return authorsRepository.findById(id).map(
            existingAuthor -> {
                Optional.ofNullable(authors.getAuthor_name()).ifPresent(existingAuthor::setAuthor_name);
                Optional.ofNullable(authors.getAuthor_info()).ifPresent(existingAuthor::setAuthor_info);
                existingAuthor.setUpdated_at(updatedCurrentTime);
                return authorsRepository.save(existingAuthor);
            }
        ).orElseThrow(() -> new RuntimeException("Authors Not Found"));
    }

    @Override
    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }
    
}

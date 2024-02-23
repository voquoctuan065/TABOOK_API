package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.AuthorEntity;
import com.tacm.tabooksapi.repository.AuthorRepository;
import com.tacm.tabooksapi.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository
                .findAll()
                .spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setAuthor_id(id);
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getAuthor_name()).ifPresent(existingAuthor::setAuthor_name);
           Optional.ofNullable(authorEntity.getAuthor_info()).ifPresent(existingAuthor::setAuthor_info);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exists"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}

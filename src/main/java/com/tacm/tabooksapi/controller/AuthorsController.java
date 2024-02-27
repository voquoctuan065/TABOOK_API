package com.tacm.tabooksapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tacm.tabooksapi.domain.dto.AuthorsDto;
import com.tacm.tabooksapi.domain.entities.Authors;
import com.tacm.tabooksapi.mapper.impl.AuthorsMapper;
import com.tacm.tabooksapi.service.AuthorsService;

@RestController
@CrossOrigin("*")
public class AuthorsController {
    
    @Autowired
    private AuthorsMapper authorsMapper;
    @Autowired
    private AuthorsService authorsService;

    @PostMapping(path="/authors")
    public AuthorsDto createAuthors(@RequestBody AuthorsDto authorsDto) {
        Authors authors = authorsMapper.mapFrom(authorsDto);
        Authors savedAuthors = authorsService.createAuthors(authors);
        return authorsMapper.mapTo(savedAuthors);
    }

    @GetMapping(path = "/authors")
    public List<AuthorsDto> listAuthors() {
        List<Authors> authors = authorsService.findAll();
        return authors.stream()
            .map(authorsMapper::mapTo)
            .collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorsDto> getAuthors(
        @PathVariable("id") Long id
    ) {
        Optional<Authors> authors = authorsService.findOne(id);
        return authors.map(
            author -> {
                AuthorsDto authorsDto = authorsMapper.mapTo(author);
                return new ResponseEntity<>(authorsDto, HttpStatus.OK);
            }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "authors/{id}")
    public ResponseEntity<AuthorsDto> partialUpdate(
        @PathVariable("id") Long id,
        @RequestBody AuthorsDto authorsDto
    ) {
        if(!authorsService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Authors authors = authorsMapper.mapFrom(authorsDto);
        Authors updatedAuthors = authorsService.partialUpdate(id, authors);
        return new ResponseEntity<>(authorsMapper.mapTo(updatedAuthors), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthors(
        @PathVariable("id") Long id
    ) {
        authorsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

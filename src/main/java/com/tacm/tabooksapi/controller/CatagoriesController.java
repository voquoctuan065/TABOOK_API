package com.tacm.tabooksapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tacm.tabooksapi.domain.dto.CatagoriesDto;
import com.tacm.tabooksapi.domain.entities.Catagories;
import com.tacm.tabooksapi.mapper.impl.CatagoriesMapper;
import com.tacm.tabooksapi.service.CatagoriesService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CatagoriesController 
{
    private CatagoriesMapper catagoriesMapper;
    private CatagoriesService catagoriesService;

    public CatagoriesController
    (
        CatagoriesMapper catagoriesMapper, 
        CatagoriesService catagoriesService
    ) 
    {
        this.catagoriesMapper = catagoriesMapper;
        this.catagoriesService = catagoriesService;    
    }

    // Create Catagories 
    @PostMapping(path="/catagories")
    public CatagoriesDto createCatagories(@RequestBody CatagoriesDto catagoriesDto) 
    {
        Catagories catagories = catagoriesMapper.mapFrom(catagoriesDto);
        Catagories savedCatagories = catagoriesService.createCategories(catagories);
        return catagoriesMapper.mapTo(savedCatagories);
    }
    // Get List Catagories
    @GetMapping(path = "/catagories")
    public List<CatagoriesDto> listCatagories() 
    {
        List<Catagories> catagories = catagoriesService.findAll();
        return catagories.stream()
        .map(catagoriesMapper::mapTo)
        .collect(Collectors.toList());
    }

    //Get One Catagories
    @GetMapping(path = "/catagories/{id}")
    public ResponseEntity<CatagoriesDto> getCategories(@PathVariable("id") Long id) {
        Optional<Catagories> foundCatagories = catagoriesService.findOne(id);
        return foundCatagories.map(catagories -> {
            CatagoriesDto catagoriesDto = catagoriesMapper.mapTo(catagories);
            return new ResponseEntity<>(catagoriesDto, HttpStatus.OK);
        })
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Partial Update Catagories
    @PatchMapping(path = "/catagories/{id}")
    public ResponseEntity<CatagoriesDto> partialUpdate(
        @PathVariable("id") Long id,
        @RequestBody CatagoriesDto cataCatagoriesDto
    ) {
        if(!catagoriesService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Catagories catagories = catagoriesMapper.mapFrom(cataCatagoriesDto);
        Catagories updatedCatagories = catagoriesService.partialUpdate(id, catagories);
        return new ResponseEntity<>(catagoriesMapper.mapTo(updatedCatagories), HttpStatus.OK);
    }

    @DeleteMapping(path = "/catagories/{id}")
    public ResponseEntity deleteCatagories(
        @PathVariable("id") Long id
    )
    {
        catagoriesService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

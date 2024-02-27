package com.tacm.tabooksapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tacm.tabooksapi.domain.entities.Catagories;
import com.tacm.tabooksapi.repository.CatagoriesRepository;
import com.tacm.tabooksapi.service.CatagoriesService;

@Service
public class CatagoriesServiceImp implements CatagoriesService {

    private CatagoriesRepository catagoriesRepository;
    

    @Autowired
    public CatagoriesServiceImp(CatagoriesRepository catagoriesRepository) {
        this.catagoriesRepository = catagoriesRepository;
    }

    @Override
    public Catagories createCategories(Catagories catagories) {
        // Create a LocalDateTime object
        LocalDateTime currentDateTime = LocalDateTime.now();
        catagories.setCreated_at(currentDateTime);
        return catagoriesRepository.save(catagories);
    }

    @Override
    public List<Catagories> findAll() {
        return StreamSupport.stream(
            catagoriesRepository.findAll()
            .spliterator(), false)
        .collect(Collectors.toList());
    }

    @Override
    public boolean isExist(Long id) {
        return catagoriesRepository.existsById(id);
    }

    @Override
    public Catagories partialUpdate(Long id, Catagories catagories){
        catagories.setId(id);
        // Create a LocalDateTime object
        LocalDateTime updateCurrentTime = LocalDateTime.now();
        return catagoriesRepository.findById(id).map(
            existingElement -> {
                Optional.ofNullable(catagories.getCatagory_name()).ifPresent(existingElement::setCatagory_name);
                Optional.ofNullable(catagories.getCatagory_description()).ifPresent(existingElement::setCatagory_description);
                existingElement.setUpdated_at(updateCurrentTime);
                return catagoriesRepository.save(existingElement);
            }
        ).orElseThrow(() -> new RuntimeException("Catagories does not exist"));
    }

    @Override
    public void delete(Long id) {
        catagoriesRepository.deleteById(id);
    }

    @Override
    public Optional<Catagories> findOne(Long id) {
        return catagoriesRepository.findById(id);
    }  
}

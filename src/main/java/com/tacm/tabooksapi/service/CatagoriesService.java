package com.tacm.tabooksapi.service;

import java.util.List;
import java.util.Optional;

import com.tacm.tabooksapi.domain.entities.Catagories;

public interface CatagoriesService {
    Catagories createCategories(Catagories categories);

    List<Catagories> findAll();

    boolean isExist(Long id);

    Catagories partialUpdate(Long id, Catagories catagories);

    void delete(Long id);

    Optional<Catagories> findOne(Long id);

}

package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.repository.CategoriesRepository;
import com.tacm.tabooksapi.service.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl (CategoriesRepository categoriesRepository, ModelMapper modelMapper) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Categories> findAll() {
        return StreamSupport.stream(categoriesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Categories create(Categories categories) {
        return null;
    }

    @Override
    public Page<Categories> getAllCategorires(PageRequest pageRequest) {
        return categoriesRepository.findAll(pageRequest);
    }

    @Override
    public Page<Categories> searchCategoriesByName(String category_name, Pageable pageable) {
        return categoriesRepository.findByCategoryNameContainingIgnoreCase(category_name, pageable);
    }
}

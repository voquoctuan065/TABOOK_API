package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriesService {
    List<Categories> findAll();

    Categories create(Categories categories);

    Page<Categories> getAllCategorires(PageRequest pageRequest);

    Page<Categories> searchCategoriesByName(String category_name, Pageable pageable);
}

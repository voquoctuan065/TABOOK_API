package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriesService {
    List<Categories> findAll();

    Categories create(Categories categories);
    

    Page<Categories> searchCategoriesByName(String category_name, Pageable pageable);

    void deleteById(Long categoryId);

    Categories updateCategory(Categories categories, Long categoryId) throws ApiException;

    Categories findCategoryById(Long categoryId) throws ApiException;

    public List<Categories> getLevelOneAndChildren();


    Page<Categories> getAllCategorires(PageRequest pageRequest);

    long getTotalCategories();
}

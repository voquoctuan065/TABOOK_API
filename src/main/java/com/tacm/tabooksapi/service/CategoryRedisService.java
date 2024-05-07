package com.tacm.tabooksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.dto.CategoriesPageDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryRedisService {
    List<CategoriesDto> findAll() throws JsonProcessingException;

    void saveAll(List<CategoriesDto> categoriesDtoList);

    List<CategoriesDto> getLevelOneAndChildren() throws JsonProcessingException;

    void saveLevelOneAndChildren(List<CategoriesDto> categoriesDtoList);

    void clear();

    CategoriesPageDto getAllCategories(PageRequest pageRequest) throws JsonProcessingException;

    void saveAllCategory(List<CategoriesDto> categoriesDtoList, PageRequest pageRequest);
}

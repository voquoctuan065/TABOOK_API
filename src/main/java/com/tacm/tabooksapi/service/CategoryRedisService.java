package com.tacm.tabooksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;

import java.util.List;

public interface CategoryRedisService {
    List<CategoriesDto> findAll() throws JsonProcessingException;

    void saveAll(List<CategoriesDto> categoriesDtoList);

    List<CategoriesDto> getLevelOneAndChildren() throws JsonProcessingException;

    void saveLevelOneAndChildren(List<CategoriesDto> categoriesDtoList);
}

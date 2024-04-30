package com.tacm.tabooksapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.mapper.impl.CategoriesMapper;
import com.tacm.tabooksapi.service.CategoriesService;
import com.tacm.tabooksapi.service.CategoryRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/public/category")
public class CategoriesController {
    private CategoriesService categoriesService;
    private CategoriesMapper categoriesMapper;

    private CategoryRedisService categoryRedisService;
    @Autowired
    public CategoriesController(CategoriesService categoriesService,
                                CategoriesMapper categoriesMapper,
                                CategoryRedisService categoryRedisService) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
        this.categoryRedisService = categoryRedisService;
    }

    @GetMapping(path = "/list-category")
    public List<CategoriesDto> listCategories() throws JsonProcessingException {
        List<CategoriesDto> categoriesDtoList = categoryRedisService.findAll();
        if(categoriesDtoList == null) {
            List<Categories> category = categoriesService.findAll();
            categoriesDtoList = category.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
            categoryRedisService.saveAll(categoriesDtoList);
        }

        return categoriesDtoList;
    }

    @GetMapping("/level1")
    public List<CategoriesDto> getLevelOneAndChildren() throws JsonProcessingException {
        List<CategoriesDto> categoriesDtoList = categoryRedisService.getLevelOneAndChildren();

        if(categoriesDtoList == null) {
            List<Categories> categories = categoriesService.getLevelOneAndChildren();
            categoriesDtoList = categories.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
            categoryRedisService.saveLevelOneAndChildren(categoriesDtoList);
        }

        return categoriesDtoList;
    }
}

package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.mapper.impl.CategoriesMapper;
import com.tacm.tabooksapi.service.CategoriesService;
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
    @Autowired
    public CategoriesController(CategoriesService categoriesService, CategoriesMapper categoriesMapper) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
    }

    @GetMapping(path = "/list-category")
    public List<CategoriesDto> listCategories() {
        List<Categories> category = categoriesService.findAll();
        return category.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/level1")
    public List<CategoriesDto> getLevelOneAndChildren() {
        List<Categories> categories = categoriesService.getLevelOneAndChildren();
        return categories.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
    }
}

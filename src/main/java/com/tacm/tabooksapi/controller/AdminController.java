package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.dto.CategoriesPageDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.mapper.impl.CategoriesMapper;
import com.tacm.tabooksapi.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private CategoriesService categoriesService;
    private CategoriesMapper categoriesMapper;
    @Autowired
    public AdminController(CategoriesService categoriesService, CategoriesMapper categoriesMapper) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
    }

    @GetMapping("/category")
    public CategoriesPageDto getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Categories> categoriesPage = categoriesService.getAllCategorires(page, size);
        List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
        int totalPages = categoriesPage.getTotalPages();

        return new CategoriesPageDto(categoriesDtoList, totalPages);
    }

    @GetMapping("/category/search")
    public CategoriesPageDto searchCategoriesByName(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Categories> categoriesPage =  categoriesService.searchCategoriesByName(keyword, PageRequest.of(page, size));
        List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
        int totalPages = categoriesPage.getTotalPages();
        return new CategoriesPageDto(categoriesDtoList, totalPages);
    }

    @PostMapping(path = "/category/add-category")
    public CategoriesDto createCategories(@RequestBody CategoriesDto categoriesDto) {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories savedCategories = categoriesService.create(categories);
        return categoriesMapper.mapTo(savedCategories);
    }

    @GetMapping(path = "/category/list-category")
    public List<CategoriesDto> listCategories() {
        List<Categories> category = categoriesService.findAll();
        return category.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
    }

    @DeleteMapping(path = "/category/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategories(@PathVariable("categoryId") Long categoryId){
        categoriesService.deleteById(categoryId);
        ApiResponse res = new ApiResponse();
        res.setMessage("category deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/category/update/{categoryId}")
    public ResponseEntity<CategoriesDto> updateCategory(@RequestBody CategoriesDto categoriesDto,
                                                     @PathVariable("categoryId") Long categoryId) throws ApiException {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories updatedCategories = categoriesService.updateCategory(categories, categoryId);

        return new ResponseEntity<>(categoriesMapper.mapTo(updatedCategories), HttpStatus.CREATED);
    }
}

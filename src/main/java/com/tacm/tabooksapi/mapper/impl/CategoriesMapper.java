package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

@Component
public class CategoriesMapper implements Mapper<Categories, CategoriesDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public CategoriesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoriesDto mapTo(Categories category) {
        return modelMapper.map(category, CategoriesDto.class);
    }

    public Categories mapFrom(CategoriesDto categoriesDto) {
        return modelMapper.map(categoriesDto, Categories.class);
    }
}

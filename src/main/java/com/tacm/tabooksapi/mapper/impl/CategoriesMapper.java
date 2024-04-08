package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriesMapper implements Mapper<Categories, CategoriesDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoriesDto mapTo(Categories categories) {
        return modelMapper.map(categories, CategoriesDto.class);
    }

    @Override
    public Categories mapFrom(CategoriesDto categoriesDto) {
        return modelMapper.map(categoriesDto, Categories.class);
    }
}

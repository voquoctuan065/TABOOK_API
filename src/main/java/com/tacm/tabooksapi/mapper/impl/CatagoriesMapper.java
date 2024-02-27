package com.tacm.tabooksapi.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacm.tabooksapi.domain.dto.CatagoriesDto;
import com.tacm.tabooksapi.domain.entities.Catagories;
import com.tacm.tabooksapi.mapper.Mapper;

@Component
public class CatagoriesMapper implements Mapper<Catagories, CatagoriesDto> {

    @Autowired
    private ModelMapper modelMapper;

    public CatagoriesMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CatagoriesDto mapTo(Catagories a) {
        return modelMapper.map(a, CatagoriesDto.class);
    }

    @Override
    public Catagories mapFrom(CatagoriesDto b) {
        return modelMapper.map(b, Catagories.class);
    }
    
}

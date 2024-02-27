package com.tacm.tabooksapi.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.mapper.Mapper;

@Component
public class NXBsMapper implements Mapper<NXBs, NXBsDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NXBsDto mapTo(NXBs a) {
        return modelMapper.map(a, NXBsDto.class);
    }

    @Override
    public NXBs mapFrom(NXBsDto b) {
        return modelMapper.map(b, NXBs.class);
    }
    
}

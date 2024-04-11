package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NXBsMapper implements Mapper<NXBs, NXBsDto> {
    private ModelMapper modelMapper;

    @Autowired
    public NXBsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public NXBsDto mapTo(NXBs nxBs) {
        return modelMapper.map(nxBs, NXBsDto.class);
    }

    @Override
    public NXBs mapFrom(NXBsDto nxBsDto) {
        return modelMapper.map(nxBsDto, NXBs.class);
    }
}

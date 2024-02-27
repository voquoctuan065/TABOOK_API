package com.tacm.tabooksapi.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacm.tabooksapi.domain.dto.AuthorsDto;
import com.tacm.tabooksapi.domain.entities.Authors;
import com.tacm.tabooksapi.mapper.Mapper;

@Component
public class AuthorsMapper  implements Mapper<Authors, AuthorsDto>{
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Authors mapFrom(AuthorsDto b) {
        return modelMapper.map(b, Authors.class);
    }

    @Override
    public AuthorsDto mapTo(Authors a) {
        return modelMapper.map(a, AuthorsDto.class);
    }
}

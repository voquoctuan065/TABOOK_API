package com.tacm.tabooksapi.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.tacm.tabooksapi.domain.dto.UserDto;
import com.tacm.tabooksapi.domain.entities.UserEntity;
import com.tacm.tabooksapi.mapper.Mapper;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
    
}

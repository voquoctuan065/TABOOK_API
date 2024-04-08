package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.RatingsDto;
import com.tacm.tabooksapi.domain.entities.Ratings;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper implements Mapper<Ratings, RatingsDto> {

    private ModelMapper modelMapper;

    @Autowired
    public RatingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RatingsDto mapTo(Ratings ratings) {
        return modelMapper.map(ratings, RatingsDto.class);
    }

    @Override
    public Ratings mapFrom(RatingsDto ratingsDto) {
        return modelMapper.map(ratingsDto, Ratings.class);
    }
}

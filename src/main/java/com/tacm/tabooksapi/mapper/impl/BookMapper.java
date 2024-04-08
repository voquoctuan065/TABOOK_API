package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<Books, BooksDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BooksDto mapTo(Books books) {
        return modelMapper.map(books, BooksDto.class);
    }

    @Override
    public Books mapFrom(BooksDto booksDto) {
        return modelMapper.map(booksDto, Books.class);
    }
}

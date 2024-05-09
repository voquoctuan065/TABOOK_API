package com.tacm.tabooksapi.mapper.impl;

import com.tacm.tabooksapi.domain.dto.*;
import com.tacm.tabooksapi.domain.entities.*;
import com.tacm.tabooksapi.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper implements Mapper<Books, BooksDto>  {

    private ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BooksDto mapTo(Books books) {
        BooksDto booksDto = modelMapper.map(books, BooksDto.class);
        CategoriesDto categoriesDto = null;
        if(books.getCategories() != null) {
            categoriesDto = CategoriesDto.builder()
                    .categoryId(books.getCategories().getCategoryId())
                    .categoryName(books.getCategories().getCategoryName())
                    .pathName(books.getCategories().getPathName())
                    .level(books.getCategories().getLevel())
                    .build();
        }
        booksDto.setCategory(categoriesDto);

        booksDto.setNxb(books.getNxbs() != null ?
                new NXBsDto(books.getNxbs().getNxbId(), books.getNxbs().getNxbName(), books.getNxbs().getNxbInfo(),
                        books.getNxbs().getCreatedAt(), books.getNxbs().getCreatedAt())
                : null);


        return booksDto;

    }

    @Override
    public Books mapFrom(BooksDto booksDto) {
        Books books = modelMapper.map(booksDto, Books.class);
        if (booksDto.getCategory() != null) {
            Categories category = new Categories();
            category.setCategoryId(booksDto.getCategory().getCategoryId());
            category.setCategoryName(booksDto.getCategory().getCategoryName());
            
            category.setPathName(booksDto.getCategory().getPathName());
            category.setLevel(booksDto.getCategory().getLevel());
            category.setCreatedAt(booksDto.getCategory().getCreatedAt());
            category.setUpdatedAt(booksDto.getCategory().getUpdatedAt());
            books.setCategories(category);
        }
        if (booksDto.getNxb() != null) {
            NXBs nxb = new NXBs();
            nxb.setNxbId(booksDto.getNxb().getNxbId());
            nxb.setNxbName(booksDto.getNxb().getNxbName());
            nxb.setNxbInfo(booksDto.getNxb().getNxbInfo());
            nxb.setCreatedAt(booksDto.getNxb().getCreatedAt());
            nxb.setUpdatedAt(booksDto.getNxb().getUpdatedAt());
            books.setNxbs(nxb);
        }
        return books;
    }
    private BooksRateDto mapRateToDto(BooksRate rate) {
        return modelMapper.map(rate, BooksRateDto.class);
    }


}

package com.tacm.tabooksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.dto.BooksPageDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookRedisService {
    void clear();
    
    void saveAllBook(List<BooksDto> booksDtoList, PageRequest pageRequest);
    
    void saveSearchingBook(List<BooksDto> booksDtoList, String keyword, PageRequest pageRequest);

    void saveBookByPathName(List<BooksDto> booksDtoList, String pathName,PageRequest pageRequest);

    BooksPageDto getAllBooks(PageRequest pageRequest) throws JsonProcessingException;

    BooksPageDto searchingBookByName(String keyword, PageRequest pageRequest) throws JsonProcessingException;

    BooksPageDto getBookByPathName(String pathName, PageRequest pageRequest) throws JsonProcessingException;

    BooksDto findBookById(Long bookId) throws JsonProcessingException;

    void saveBookById(BooksDto booksDto, Long bookId);
}

package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.RatingResDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.BooksRate;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.BooksRateRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.BooksRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BooksRateServiceImpl implements BooksRateService {

    private BookService bookService;
    private BooksRateRepository booksRateRepository;

    @Autowired
    public BooksRateServiceImpl(BookService bookService, BooksRateRepository booksRateRepository) {
        this.bookService = bookService;
        this.booksRateRepository = booksRateRepository;
    }
    @Override
    public void createRate(Long bookId, RatingResDto ratingResDto, Users users) throws ProductException {
        Books books = bookService.findBookById(bookId);

        BooksRate booksRate = new BooksRate();
        booksRate.setBooks(books);
        booksRate.setUser(users);
        booksRate.setComment(ratingResDto.getComment());
        booksRate.setRating(ratingResDto.getRating());
        booksRate.setCreatedAt(LocalDateTime.now());

        booksRateRepository.save(booksRate);
    }
}

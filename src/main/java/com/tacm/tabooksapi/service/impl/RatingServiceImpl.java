package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.RatingsDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Ratings;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.RatingRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private BookService bookService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, BookService bookService) {
        this.ratingRepository = ratingRepository;
        this.bookService = bookService;
    }

    @Override
    public Ratings createRating(RatingsDto ratingDto, Users users) throws ProductException {
        Books books = bookService.findBookById(ratingDto.getBookId());
        Ratings ratings = new Ratings();
        ratings.setBooks(books);
        ratings.setRating(ratingDto.getRating());
        ratings.setUsers(users);
        ratings.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(ratings);
    }

    @Override
    public List<Ratings> getBookRating(Long book_id) {
        return ratingRepository.getAllBookRating(book_id);
    }
}

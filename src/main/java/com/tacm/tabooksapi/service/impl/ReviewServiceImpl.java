package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.dto.ReviewsDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Reviews;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.ReviewRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private BookService bookService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
    }
    @Override
    public Reviews createReview(ReviewsDto reviewsDto, Users users) throws ProductException {
        Books books = bookService.findBookById(reviewsDto.getBook_id());
        Reviews reviews = new Reviews();

        reviews.setUsers(users);
        reviews.setBooks(books);
        reviews.setReview(reviews.getReview());
        reviews.setCreated_at(LocalDateTime.now());
        return reviewRepository.save(reviews);
    }

    @Override
    public List<Reviews> getAllReview(Long book_id) {
        return reviewRepository.getAllBookReview(book_id);
    }
}

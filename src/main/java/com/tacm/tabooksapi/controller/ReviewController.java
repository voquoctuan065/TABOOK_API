package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ReviewsDto;
import com.tacm.tabooksapi.domain.entities.Reviews;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.ReviewService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/review")
public class ReviewController {
    private ReviewService reviewService;
    private UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Reviews> createReview(@RequestBody ReviewsDto reviewsDto
    , @RequestHeader("Authorization") String token) throws UserException, ProductException {
        Users users = userService.findUserProfileByJwt(token);
        Reviews reviews = reviewService.createReview(reviewsDto, users);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/book/{book_id}")
    public ResponseEntity<List<Reviews>> getBookReview(@PathVariable("book_id") Long book_id)
    throws  ProductException{
        List<Reviews> reviews = reviewService.getAllReview(book_id);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}

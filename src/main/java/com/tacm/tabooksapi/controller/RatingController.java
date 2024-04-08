package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.RatingsDto;
import com.tacm.tabooksapi.domain.entities.Ratings;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.mapper.impl.RatingMapper;
import com.tacm.tabooksapi.service.RatingService;
import com.tacm.tabooksapi.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin("*")
public class RatingController {
    private UserService userService;
    private RatingService ratingService;
    @Autowired
    public RatingController(UserService userService, RatingService ratingService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Ratings> createRating(@RequestBody RatingsDto ratingsDto, @RequestHeader("Authorization") String token) throws UserException, ProductException {
        Users users = userService.findUserProfileByJwt(token);
        Ratings savedRatings = ratingService.createRating(ratingsDto, users);

        return new ResponseEntity<>(savedRatings, HttpStatus.CREATED);
    }

    @GetMapping("/book/{book_id}")
    public ResponseEntity<List<Ratings>> listBookRating(@PathVariable("book_id") Long book_id,
                                                        @RequestHeader("Authorization") String token)
            throws ProductException, UserException {
        Users users = userService.findUserProfileByJwt(token);
        List<Ratings> listRating = ratingService.getBookRating(book_id);
        return new ResponseEntity<>(listRating, HttpStatus.OK);
    }
}

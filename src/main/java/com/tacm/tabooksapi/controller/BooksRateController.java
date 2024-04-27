package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.dto.RatingResDto;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.UserException;
import com.tacm.tabooksapi.service.BooksRateService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/rate")
public class BooksRateController {

    private UserService userService;
    private BooksRateService booksRateService;
    @Autowired
    public BooksRateController(UserService userService, BooksRateService booksRateService) {
        this.userService = userService;
        this.booksRateService = booksRateService;
    }


    @PostMapping("/create/{bookId}")
    public ResponseEntity<ApiResponse> createRate(@PathVariable("bookId") Long bookId,
                                                  @RequestBody RatingResDto ratingResDto,
                                                  @RequestHeader("Authorization") String jwt) throws UserException, ApiException {
        Users users = userService.findUserProfileByJwt(jwt);
        try {
             booksRateService.createRate(bookId, ratingResDto, users);
             ApiResponse res = new ApiResponse();
             res.setMessage("Successfully");
             res.setStatus(true);
             return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ApiException("ERROR", HttpStatus.BAD_REQUEST);
        }
    }
}

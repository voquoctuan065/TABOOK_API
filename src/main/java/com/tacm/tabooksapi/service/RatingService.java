package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.RatingsDto;
import com.tacm.tabooksapi.domain.entities.Ratings;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;

import java.util.List;

public interface RatingService {
    Ratings createRating(RatingsDto ratingDto, Users users)  throws ProductException;
    List<Ratings> getBookRating(Long book_id);
}

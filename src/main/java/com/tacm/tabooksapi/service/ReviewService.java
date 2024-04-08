package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.ReviewsDto;
import com.tacm.tabooksapi.domain.entities.Reviews;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;

import java.util.List;

public interface ReviewService {
    Reviews createReview(ReviewsDto reviewsDto, Users users) throws ProductException;

    List<Reviews> getAllReview(Long book_id);
}

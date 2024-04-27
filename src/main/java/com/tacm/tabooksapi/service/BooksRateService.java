package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.RatingResDto;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.ProductException;

public interface BooksRateService {
    void createRate(Long bookId, RatingResDto ratingResDto, Users users) throws ProductException;
}

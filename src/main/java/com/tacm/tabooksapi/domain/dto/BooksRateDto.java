package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksRateDto {
    private Long rateId;
    private UserDto user;
    private BooksDto book;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;
}

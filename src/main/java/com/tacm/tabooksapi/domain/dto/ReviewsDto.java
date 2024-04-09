package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsDto {
    private Long bookId;
    private String review;
}

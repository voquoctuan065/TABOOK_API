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
public class RatingsDto {
    private Long bookId;
    private double rating;
}

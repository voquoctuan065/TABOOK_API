package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResDto {
    private Double rating;
    private String comment;
}

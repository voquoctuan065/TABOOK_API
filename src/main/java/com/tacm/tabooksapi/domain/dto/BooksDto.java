package com.tacm.tabooksapi.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.tacm.tabooksapi.domain.entities.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksDto {
    private Long book_id;
    private String book_title;
    private String book_description;
    private String book_image;

    private CategoriesDto category;
    private String author_name;
    private NXBsDto nxb;

    private List<RatingsDto> ratings;
    private List<ReviewsDto> reviews;
    private Integer num_rating;

    private Integer year_produce;
    private Integer stock_quantity;
    private BigDecimal book_price;
    private BigDecimal discounted_price;
    private BigDecimal discount_percent;
    private boolean hot;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

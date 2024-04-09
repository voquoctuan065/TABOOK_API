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
    private Long bookId;
    private String bookTitle;
    private String bookDescription;
    private String bookImage;

    private CategoriesDto category;
    private String authorName;
    private NXBsDto nxb;

    private List<RatingsDto> ratings;
    private List<ReviewsDto> reviews;
    private Integer numRating;

    private Integer yearProduce;
    private Integer stockQuantity;
    private BigDecimal bookPrice;
    private BigDecimal discountedPrice;
    private BigDecimal discountPercent;
    private boolean hot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

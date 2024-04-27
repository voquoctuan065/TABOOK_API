package com.tacm.tabooksapi.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacm.tabooksapi.domain.entities.BooksRate;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Tên sách không được để trống")
    @Size(max = 255, message = "Tên sách không được vượt quá 255 ký tự")
    @Pattern(regexp = "^[^@#$%^&*()\\\\d]+$", message = "Tên sách không được chứa ký tự đặc biệt hoặc số")
    private String bookTitle;

    private String bookDescription;

    private String bookImage;

    private CategoriesDto category;

    @NotBlank(message = "Tên tác giả không được để trống")
    @Size(max = 255, message = "Tên tác giả không được vượt quá 255 ký tự")
    @JsonProperty("authorName")
    private String authorName;

    private NXBsDto nxb;

    private Integer numRating;

    private List<BooksRateDto> reviews = new ArrayList<>();

    @NotNull(message = "Năm xuất bản không được để trống")
    private Integer yearProduce;

    @NotNull(message = "Số lượng trong kho không được để trống")
    @PositiveOrZero(message = "Số lượng trong kho không được âm")
    @Max(value = 100, message = "Số lượng trong kho không được lớn hơn 100")
    private Integer stockQuantity;

    @NotNull(message = "Giá sách không được để trống")
    @Positive(message = "Giá sách phải là số dương")
    @Max(value = 2000000, message = "Giá sách không được lớn hơn 2 triệu đồng")
    private BigDecimal bookPrice;

    @NotNull(message = "Giá sau khi giảm không được để trống")
    private BigDecimal discountedPrice;

    @NotNull(message = "% giảm giá không được để trống")
    @Min(value = 0, message = "% giảm giá không được nhỏ hơn 0")
    @Max(value = 100, message = "% giảm giá không được lớn hơn 100")
    private BigDecimal discountPercent;
    private boolean hot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

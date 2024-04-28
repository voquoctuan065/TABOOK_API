package com.tacm.tabooksapi.domain.dto;

import com.tacm.tabooksapi.domain.entities.Books;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDto {
    private Long bookId;
    private String bookTitle;
    private String bookImage;
    private Double bookPrice;
    private Double discountedPrice;
    private Integer discountPercent;
    public static BookOrderDto fromEntity(Books books) {
        return new BookOrderDto(
                books.getBookId(),
                books.getBookTitle(),
                books.getBookImage(),
                books.getBookPrice(),
                books.getDiscountedPrice(),
                books.getDiscountPercent()
        );
    }
}

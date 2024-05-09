package com.tacm.tabooksapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.domain.entities.NXBs;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookReq {
    private Long bookId;

    private String bookTitle;

    private String bookDescription;

    private String bookImage;

    private Long categoryId;


    private String authorName;

    private Long nxbId;

    private Integer numRating;
    private Integer yearProduce;

    private Integer stockQuantity;
    private Double bookPrice;
    private Double discountedPrice;
    private Integer discountPercent;
    private boolean hot;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookReq fromEntity(Books books) {
        return new BookReq(
                books.getBookId(),
                books.getBookTitle(),
                books.getBookDescription(),
                books.getBookImage(),
                books.getCategories().getCategoryId(),
                books.getAuthorName(),
                books.getNxbs().getNxbId(),
                books.getNumRating(),
                books.getYearProduce(),
                books.getStockQuantity(),
                books.getBookPrice(),
                books.getDiscountedPrice(),
                books.getDiscountPercent(),
                books.isHot(),
                books.getCreatedAt(),
                books.getUpdatedAt()
        );
    }

    public static Books fromDto(BookReq bookReq) {
        Books book = new Books();
        book.setBookId(bookReq.getBookId());
        book.setBookTitle(bookReq.getBookTitle());
        book.setBookDescription(bookReq.getBookDescription());
        book.setBookImage(bookReq.getBookImage());
        // Assuming Categories and Nxbs are associations in the Books entity
        Categories category = new Categories();
        category.setCategoryId(bookReq.getCategoryId());
        book.setCategories(category);
        book.setAuthorName(bookReq.getAuthorName());
        // Similarly, assuming Nxbs is an association
        NXBs nxb = new NXBs();
        nxb.setNxbId(bookReq.getNxbId());
        book.setNxbs(nxb);
        book.setNumRating(bookReq.getNumRating());
        book.setYearProduce(bookReq.getYearProduce());
        book.setStockQuantity(bookReq.getStockQuantity());
        book.setBookPrice(bookReq.getBookPrice());
        book.setDiscountedPrice(bookReq.getDiscountedPrice());
        book.setDiscountPercent(bookReq.getDiscountPercent());
        book.setHot(bookReq.isHot());
        book.setCreatedAt(bookReq.getCreatedAt());
        book.setUpdatedAt(bookReq.getUpdatedAt());
        return book;
    }
}


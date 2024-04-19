package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@JsonSerialize
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_title",nullable = false)
    private String bookTitle;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name="book_image")
    private String bookImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Categories categories;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @ManyToOne()
    @JoinColumn(name = "nxb_id", nullable = false)
    private NXBs nxbs;

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ratings> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    @Column(name = "num_rating")
    private Integer numRating;

    @Column(name = "year_produce", nullable = false)
    private Integer yearProduce;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "book_price", nullable = false)
    private Double bookPrice;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "hot")
    private boolean hot;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

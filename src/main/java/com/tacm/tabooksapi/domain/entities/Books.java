package com.tacm.tabooksapi.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;
    @Column(nullable = false)
    private String book_title;
    private String book_description;
    private String book_image;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @Column(name = "author_name", nullable = false)
    private String author_name;

    @ManyToOne()
    @JoinColumn(name = "nxb_id", nullable = false)
    private NXBs nxbs;

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ratings> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    private Integer num_rating;
    @Column(nullable = false)
    private Integer year_produce;
    @Column(nullable = false)
    private Integer stock_quantity;
    @Column(nullable = false)
    private Double book_price;
    private Double discounted_price;
    private Integer discount_percent;
    private boolean hot;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

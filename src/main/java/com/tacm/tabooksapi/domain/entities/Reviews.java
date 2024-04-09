package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review")
    private String review;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Books books;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

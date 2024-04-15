package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties({"parentCategory", "books"})
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Categories parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Categories> children = new ArrayList<>();

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Books> books = new ArrayList<>();

    @Column(name = "level")
    private Integer level;

    @Column(name = "create_at")
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

}

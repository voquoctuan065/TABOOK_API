package com.tacm.tabooksapi.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesDto {
    private Long categoryId;
    private String categoryName;
    private Categories parentCategory;
    private List<Categories> children = new ArrayList<>();
    private String pathName;
    private Integer level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

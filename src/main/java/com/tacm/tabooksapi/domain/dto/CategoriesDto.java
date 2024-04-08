package com.tacm.tabooksapi.domain.dto;

import java.time.LocalDateTime;

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
    private Long category_id;
    private String category_name;
    private Categories parentCategory;
    private Integer level;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

package com.tacm.tabooksapi.domain.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesPageDto {
    private List<CategoriesDto> categoriesDtoList;
    private int totalPages;
}

package com.tacm.tabooksapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksPageDto {
    private List<BooksDto> booksDtoList;
    private int totalPage;
}

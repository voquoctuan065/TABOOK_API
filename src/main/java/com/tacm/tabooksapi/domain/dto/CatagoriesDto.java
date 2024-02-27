package com.tacm.tabooksapi.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatagoriesDto {
    private Long id;
    private String catagory_name;
    private String catagory_description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

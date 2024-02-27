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
public class AuthorsDto {
    private Long author_id;
    private String author_name;
    private String author_info;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

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
public class NXBsDto {
    private Long nxb_id;
    private String nxb_name;
    private String nxb_info;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}

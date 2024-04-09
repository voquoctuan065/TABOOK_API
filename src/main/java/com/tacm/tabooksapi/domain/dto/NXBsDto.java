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
    private Long nxbId;
    private String nxbName;
    private String nxbInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class NXBs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nxb_id;
    private String nxb_name;
    private String nxb_info;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
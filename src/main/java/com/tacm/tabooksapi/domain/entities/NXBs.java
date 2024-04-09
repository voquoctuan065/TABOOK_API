package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;


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
public class NXBs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nxb_id", nullable = false)
    private Long nxbId;

    @Column(name = "nxb_name")
    private String nxbName;

    @Column(name = "nxb_info")
    private String nxbInfo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
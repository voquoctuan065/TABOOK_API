package com.tacm.tabooksapi.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String user_fullname;
    private String email;
    private String password;
    private String user_province;
    private String user_address;
    private String user_phone;
    private String user_photo;
    private boolean is_locked;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String role;
}

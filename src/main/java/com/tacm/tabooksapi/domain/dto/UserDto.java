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
public class UserDto {
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

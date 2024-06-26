package com.tacm.tabooksapi.domain.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_image")
    private String userImage;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("users")
    private List<Orders> orders;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

}

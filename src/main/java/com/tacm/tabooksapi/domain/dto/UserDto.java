package com.tacm.tabooksapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tacm.tabooksapi.domain.entities.Address;
import com.tacm.tabooksapi.domain.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String userImage;
    private List<AddressDto> address = new ArrayList<>();

    public static UserDto fromEntity(Users users) {
        return new UserDto(
            users.getUserId(),
                users.getFullName(),
                users.getEmail(),
                users.getPassword(),
                users.getRole(),
                users.getPhoneNumber(),
                users.getUserImage(),
                users.getAddress().stream().map(AddressDto::fromEntity).collect(Collectors.toList())
        );
    }
}

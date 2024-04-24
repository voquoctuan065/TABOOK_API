package com.tacm.tabooksapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name="street_address")
    private String streetAddress;
    @Column(name = "ward")
    private String ward;
    @Column(name = "district")
    private String district;
    @Column(name = "province")
    private String province;
    @Column(name = "zip_code")
    private String zipCode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name="phone_number")
    private String phoneNumber;
}

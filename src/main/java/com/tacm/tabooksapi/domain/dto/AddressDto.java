package com.tacm.tabooksapi.domain.dto;


import com.tacm.tabooksapi.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long addressId;
    private String fullName;
    private String streetAddress;
    private String ward;
    private String district;
    private String province;
    private String zipCode;
    private String phoneNumber;

    public static AddressDto fromEntity(Address address) {
        return new AddressDto(
                address.getAddressId(),
                address.getFullName(),
                address.getStreetAddress(),
                address.getWard(),
                address.getDistrict(),
                address.getProvince(),
                address.getZipCode(),
                address.getPhoneNumber()
        );
    }

    public static Address fromDto(AddressDto addressDto) {
        return new Address(
                addressDto.getAddressId(),
                addressDto.getFullName(),
                addressDto.getStreetAddress(),
                addressDto.getWard(),
                addressDto.getDistrict(),
                addressDto.getProvince(),
                addressDto.getZipCode(),
                null,
                addressDto.getPhoneNumber()
        );
    }
}

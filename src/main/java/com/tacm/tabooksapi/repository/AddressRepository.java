package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByFullNameAndStreetAddressAndWardAndDistrictAndProvinceAndZipCodeAndPhoneNumber(
            String fullName,
            String streetAddress,
            String ward,
            String district,
            String province,
            String zipCode,
            String phoneNumber
    );
}

package com.petshop.company.domain.model;

import com.petshop.company.domain.exception.InvalidCepException;
import com.petshop.company.domain.model.enums.AddressType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "adress_type")
    private AddressType addressType;

    public Address() {
    }

    public void setZipCode(String zipCode) {
        this.zipCode = validateZipCode(zipCode);
    }

    private String validateZipCode(String zipCode) {
        if (zipCode == null || zipCode.length() < 8)
            throw new InvalidCepException("CEP must contain exactly 8 digits");
        return zipCode;
    }
}

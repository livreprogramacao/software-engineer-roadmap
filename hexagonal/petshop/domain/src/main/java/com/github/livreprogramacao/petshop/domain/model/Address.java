package com.github.livreprogramacao.petshop.domain.model;

import com.github.livreprogramacao.petshop.domain.model.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    private Long id;

    private String street;

    private String number;

    private String city;

    private String state;

    private String zipCode;

    private AddressType addressType;

    public Address() {
    }

}
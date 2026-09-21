package com.petshop.company.adapter.out.persistence.mapper;

import com.petshop.company.domain.dto.CustomerDTO;
import com.petshop.company.domain.model.Customer;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCpf(customer.getCpf());
        dto.setName(customer.getName());
        dto.setBornDate(customer.getBornDate());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setCpf(dto.getCpf());
        customer.setName(dto.getName());
        customer.setBornDate(dto.getBornDate());
        customer.setCreatedAt(dto.getCreatedAt());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        return customer;
    }

}
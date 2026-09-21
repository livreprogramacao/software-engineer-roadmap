package com.petshop.company.domain.port.in;

import com.petshop.company.domain.dto.CreateCustomerRequest;
import com.petshop.company.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerUseCase {
    CustomerDTO createCustomer(CreateCustomerRequest request);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO updateCustomer(Long id, CreateCustomerRequest request);
    void deleteCustomer(Long id);
    boolean existsByCpf(String CPF);
}

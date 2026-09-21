package com.github.livreprogramacao.petshop.domain.port.out;

import com.github.livreprogramacao.petshop.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> findAllWithAddress();
    Optional<Customer> findById(Long id);
    void delete(Customer customer);
    boolean existsByCpf(String CPF);
}

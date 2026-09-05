package com.petshop.company.domain.port.out;

import com.petshop.company.domain.model.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    @Query("SELECT c FROM Customer c JOIN FETCH c.address")
    List<Customer> findAllWithAddress();
    Optional<Customer> findById(Long id);
    void delete(Customer customer);
    boolean existsByCpf(String CPF);
}

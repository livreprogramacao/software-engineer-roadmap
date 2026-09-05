package com.petshop.company.application.service;

import com.petshop.company.adapter.out.persistence.mapper.CustomerMapper;
import com.petshop.company.domain.dto.CreateCustomerRequest;
import com.petshop.company.domain.dto.CustomerDTO;
import com.petshop.company.domain.exception.CpfAlreadyExistsException;
import com.petshop.company.domain.exception.CustomerNotFoundException;
import com.petshop.company.domain.model.Address;
import com.petshop.company.domain.model.Customer;
import com.petshop.company.domain.port.in.CustomerUseCase;
import com.petshop.company.domain.port.out.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerUseCase {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        log.info("Creating customer: {}", request.getName());
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());

        if (existsByCpf(request.getCpf())) {
            throw new CpfAlreadyExistsException(request.getCpf());
        }
        customer.setCpf(request.getCpf());
        customer.setBornDate(request.getBornDate());

        Address address = new Address();
        address.setStreet(request.getAddress().getStreet());
        address.setNumber(request.getAddress().getNumber());
        address.setCity(request.getAddress().getCity());
        address.setState(request.getAddress().getState());
        address.setZipCode(request.getAddress().getZipCode());
        address.setAddressType(request.getAddress().getAddressType());
        customer.setAddress(address);

        Customer saved = this.repository.save(customer);
        log.info("Customer created with id: {}", saved.getId());
        return CustomerMapper.toDTO(saved);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers");
        List<CustomerDTO> customers = this.repository
                .findAllWithAddress()
                .stream()
                .map(CustomerMapper::toDTO)
                .toList();
        log.info("Found {} customers", customers.size());
        return customers;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        log.info("Fetching customer with id: {}", id);
        return CustomerMapper.toDTO(findCustomerById(id));
    }

    private Customer findCustomerById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new CustomerNotFoundException(id);
                });
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CreateCustomerRequest request) {
        log.info("Updating customer with id: {}", id);
        Customer customer = findCustomerById(id);
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setCpf(request.getCpf());
        customer.setBornDate(request.getBornDate());

        Address address = customer.getAddress();
        if (address == null) {
            address = new Address();
        }
        address.setStreet(request.getAddress().getStreet());
        address.setNumber(request.getAddress().getNumber());
        address.setCity(request.getAddress().getCity());
        address.setState(request.getAddress().getState());
        address.setZipCode(request.getAddress().getZipCode());
        address.setAddressType(request.getAddress().getAddressType());
        customer.setAddress(address);

        Customer customerSaved = repository.save(customer);
        log.info("Customer {} updated successfully", id);
        return CustomerMapper.toDTO(customerSaved);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        Customer customer = findCustomerById(id);
        this.repository.delete(customer);
        log.info("Customer {} deleted successfully", id);
    }

    @Override
    public boolean existsByCpf(String CPF) {
        return repository.existsByCpf(CPF);
    }
}

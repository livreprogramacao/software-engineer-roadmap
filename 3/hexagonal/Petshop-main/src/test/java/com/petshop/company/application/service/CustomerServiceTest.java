package com.petshop.company.application.service;

import com.petshop.company.domain.dto.CreateCustomerRequest;
import com.petshop.company.domain.dto.CreateAddressRequest;
import com.petshop.company.domain.dto.CustomerDTO;
import com.petshop.company.domain.exception.CustomerNotFoundException;
import com.petshop.company.domain.model.Customer;
import com.petshop.company.domain.model.enums.AddressType;
import com.petshop.company.domain.port.out.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerRequest createSampleRequest() {
        CreateAddressRequest addressRequest = new CreateAddressRequest();
        addressRequest.setStreet("Rua A");
        addressRequest.setNumber("123");
        addressRequest.setCity("São Paulo");
        addressRequest.setState("SP");
        addressRequest.setZipCode("01234567");
        addressRequest.setAddressType(AddressType.HOME);

        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("João Silva");
        request.setPhone("11999998888");
        request.setCpf("12345678909");
        request.setBornDate(LocalDate.of(1990, 5, 15));
        request.setAddress(addressRequest);
        return request;
    }

    private Customer createSampleCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("João Silva");
        customer.setPhone("11999998888");
        customer.setCpf("12345678909");
        customer.setBornDate(LocalDate.of(1990, 5, 15));
        return customer;
    }

    @Test
    void createCustomer_shouldSaveAndReturnDTO() {
        CreateCustomerRequest request = createSampleRequest();
        Customer customer = createSampleCustomer();
        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.createCustomer(request);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals("12345678909", result.getCpf());
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    void getAllCustomers_shouldReturnListOfDTOs() {
        Customer customer1 = createSampleCustomer();
        Customer customer2 = createSampleCustomer();
        customer2.setId(2L);
        customer2.setName("Maria Santos");

        when(repository.findAllWithAddress()).thenReturn(List.of(customer1, customer2));

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        assertEquals("João Silva", result.get(0).getName());
        assertEquals("Maria Santos", result.get(1).getName());
        verify(repository, times(1)).findAllWithAddress();
    }

    @Test
    void getCustomerById_whenExists_shouldReturnDTO() {
        Customer customer = createSampleCustomer();
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_whenNotExists_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(99L));
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void updateCustomer_whenExists_shouldUpdateAndReturnDTO() {
        CreateCustomerRequest request = createSampleRequest();
        request.setName("João Updated");
        request.setPhone("11888887777");

        Customer existing = createSampleCustomer();
        Customer updated = createSampleCustomer();
        updated.setName("João Updated");
        updated.setPhone("11888887777");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerDTO result = customerService.updateCustomer(1L, request);

        assertEquals("João Updated", result.getName());
        assertEquals("11888887777", result.getPhone());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existing);
    }

    @Test
    void updateCustomer_whenNotExists_shouldThrowException() {
        CreateCustomerRequest request = createSampleRequest();
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(99L, request));
        verify(repository, never()).save(any());
    }

    @Test
    void deleteCustomer_whenExists_shouldDelete() {
        Customer customer = createSampleCustomer();
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomer_whenNotExists_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(99L));
        verify(repository, never()).delete(any());
    }
}

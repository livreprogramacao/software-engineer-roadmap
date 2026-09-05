package com.petshop.company.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.petshop.company.domain.dto.CreateCustomerRequest;
import com.petshop.company.domain.dto.CreateAddressRequest;
import com.petshop.company.domain.dto.CustomerDTO;
import com.petshop.company.domain.exception.CustomerNotFoundException;
import com.petshop.company.domain.model.enums.AddressType;
import com.petshop.company.domain.port.in.CustomerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerUseCase customerUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

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

    private CustomerDTO createSampleCustomerDTO() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(1L);
        dto.setName("João Silva");
        dto.setPhone("11999998888");
        dto.setCpf("12345678909");
        dto.setBornDate(LocalDate.of(1990, 5, 15));
        return dto;
    }

    @Test
    void createCustomer_shouldReturn201WithCustomer() throws Exception {
        CustomerDTO customerDTO = createSampleCustomerDTO();
        when(customerUseCase.createCustomer(any(CreateCustomerRequest.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678909"))
                .andExpect(jsonPath("$.id").value(1));

        verify(customerUseCase, times(1)).createCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    void getAllCustomers_shouldReturn200WithList() throws Exception {
        CustomerDTO customer = createSampleCustomerDTO();
        when(customerUseCase.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("João Silva"));

        verify(customerUseCase, times(1)).getAllCustomers();
    }

    @Test
    void getCustomerById_whenExists_shouldReturn200() throws Exception {
        CustomerDTO customer = createSampleCustomerDTO();
        when(customerUseCase.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.id").value(1));

        verify(customerUseCase, times(1)).getCustomerById(1L);
    }

    @Test
    void getCustomerById_whenNotExists_shouldReturn404() throws Exception {
        when(customerUseCase.getCustomerById(99L))
                .thenThrow(new CustomerNotFoundException(99L));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found with id: 99"));

        verify(customerUseCase, times(1)).getCustomerById(99L);
    }

    @Test
    void updateCustomer_shouldReturn200WithUpdatedCustomer() throws Exception {
        CustomerDTO updated = createSampleCustomerDTO();
        updated.setName("João Updated");
        when(customerUseCase.updateCustomer(eq(1L), any(CreateCustomerRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSampleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Updated"));

        verify(customerUseCase, times(1)).updateCustomer(eq(1L), any(CreateCustomerRequest.class));
    }

    @Test
    void deleteCustomer_shouldReturn204() throws Exception {
        doNothing().when(customerUseCase).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());

        verify(customerUseCase, times(1)).deleteCustomer(1L);
    }

    @Test
    void deleteCustomer_whenNotExists_shouldReturn404() throws Exception {
        doThrow(new CustomerNotFoundException(99L))
                .when(customerUseCase).deleteCustomer(99L);

        mockMvc.perform(delete("/api/customers/99"))
                .andExpect(status().isNotFound());

        verify(customerUseCase, times(1)).deleteCustomer(99L);
    }
}

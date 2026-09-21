package com.petshop.company.adapter.in.web.controller;

import com.petshop.company.domain.dto.CreateCustomerRequest;
import com.petshop.company.domain.dto.CustomerDTO;
import com.petshop.company.domain.port.in.CustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "CRUD de clientes")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerUseCase customerUseCase;

    public CustomerController(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente com endereço associado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado")
    })
    public ResponseEntity<CustomerDTO> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente a ser criado")
            @Valid @RequestBody CreateCustomerRequest request) {
        log.info("POST /api/customers - name: {}", request.getName());
        CustomerDTO created = customerUseCase.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista de todos os clientes com seus endereços")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        log.info("GET /api/customers");
        return ResponseEntity.ok(customerUseCase.getAllCustomers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        log.info("GET /api/customers/{}", id);
        return ResponseEntity.ok(customerUseCase.getCustomerById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "ID do cliente") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do cliente")
            @Valid @RequestBody CreateCustomerRequest request) {
        log.info("PUT /api/customers/{}", id);
        return ResponseEntity.ok(customerUseCase.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove um cliente pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID do cliente") @PathVariable Long id) {
        log.info("DELETE /api/customers/{}", id);
        customerUseCase.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists-cpf/{cpf}")
    @Operation(summary = "Verificar se CPF existe", description = "Verifica se já existe um cliente cadastrado com o CPF informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    })
    public ResponseEntity<Boolean> existsByCpf(
            @Parameter(description = "CPF a ser consultado") @PathVariable String cpf) {
        log.info("GET /api/customers/exists-cpf/{}", cpf);
        return ResponseEntity.ok(customerUseCase.existsByCpf(cpf));
    }
}

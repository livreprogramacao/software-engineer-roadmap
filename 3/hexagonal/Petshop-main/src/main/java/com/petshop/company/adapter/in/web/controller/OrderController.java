package com.petshop.company.adapter.in.web.controller;

import com.petshop.company.domain.dto.CreateOrderRequest;
import com.petshop.company.domain.dto.OrderDTO;
import com.petshop.company.domain.dto.UpdateOrderStatusRequest;
import com.petshop.company.domain.port.in.OrderUseCase;
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
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Gestão de pedidos")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido e envia para processamento assíncrono via fila")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<OrderDTO> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do pedido a ser criado")
            @Valid @RequestBody CreateOrderRequest request) {
        log.info("POST /api/orders - customer: {}, productId: {}", request.getCustomerName(), request.getProductId());
        OrderDTO created = orderUseCase.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna a lista de todos os pedidos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("GET /api/orders");
        return ResponseEntity.ok(orderUseCase.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        log.info("GET /api/orders/{}", id);
        return ResponseEntity.ok(orderUseCase.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido com validação de transições permitidas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Transição de status inválida"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novo status do pedido")
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        log.info("PATCH /api/orders/{}/status - newStatus: {}", id, request.getStatus());
        return ResponseEntity.ok(orderUseCase.updateOrderStatus(id, request.getStatus()));
    }
}

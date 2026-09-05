package com.petshop.company.domain.dto;

import com.petshop.company.domain.model.Product;
import com.petshop.company.domain.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Representação de um pedido")
public class OrderDTO {

    @Schema(description = "ID do pedido", example = "1")
    private Long id;

    @Schema(description = "Nome do cliente", example = "João Silva")
    private String customerName;

    @Schema(description = "Produto solicitado")
    private Product product;

    @Schema(description = "Quantidade solicitada", example = "2")
    private Integer quantity;

    @Schema(description = "Status atual do pedido", example = "PENDING")
    private OrderStatus status;
}

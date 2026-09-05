package com.github.livreprogramacao.petshop.application.jpa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para criação de pedido")
public class CreateOrderRequest {

    @NotNull
    @Schema(description = "ID do produto a ser pedido", example = "1")
    private Long productId;

    @NotBlank
    @Schema(description = "Nome do cliente que realiza o pedido", example = "João Silva")
    private String customerName;

    @NotNull
    @Min(1)
    @Schema(description = "Quantidade do produto", example = "2", minimum = "1")
    private Integer quantity;
}

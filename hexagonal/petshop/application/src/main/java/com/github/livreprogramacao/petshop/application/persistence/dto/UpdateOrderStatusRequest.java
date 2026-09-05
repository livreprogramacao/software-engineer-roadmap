package com.github.livreprogramacao.petshop.application.jpa.dto;

import com.petshop.company.domain.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados para atualização de status do pedido")
public class UpdateOrderStatusRequest {

    @NotNull
    @Schema(description = "Novo status do pedido", example = "PROCESSING")
    private OrderStatus status;
}

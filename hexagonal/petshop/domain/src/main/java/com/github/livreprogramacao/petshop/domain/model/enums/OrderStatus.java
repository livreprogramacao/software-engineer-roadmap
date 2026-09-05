package com.github.livreprogramacao.petshop.domain.model.enums;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Set;

//@Schema(description = "Status do pedido")
public enum OrderStatus {

    //@Schema(description = "Pedido pendente de processamento")
    FAILED(4, "FAILED", Set.of()),

    //@Schema(description = "Pedido concluído com sucesso")
    COMPLETED(3, "COMPLETED", Set.of()),

    //@Schema(description = "Pedido em processamento")
    PROCESSING(2, "PROCESSING", Set.of(COMPLETED, FAILED)),

    //@Schema(description = "Pedido pendente de processamento")
    PENDING(1, "PENDING", Set.of(PROCESSING, FAILED));

    private final int value;
    @Getter
    private final String dbValue;
    private Set<OrderStatus> allowedTransactions = null;

    OrderStatus(int value, String dbValue, Set<OrderStatus> allowed){
        this.value = value;
        this.dbValue = dbValue;
        this.allowedTransactions = allowed;
    }

    public boolean canTransactionTo(OrderStatus target){

        return allowedTransactions.contains(target);

    }

}

package com.github.livreprogramacao.petshop.domain.model.enums;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

//@Schema(description = "Tipo de endereço")
public enum AddressType {

    //@Schema(description = "Residencial")
    HOME(1, "HOME"),

    //@Schema(description = "Comercial")
    JOB(2, "JOB"),

    //@Schema(description = "Outros")
    OTHERS(3, "OTHERS");

    private final int value;
    @Getter
    private final String dbValue;

    AddressType(int value, String dbValue){
        this.value = value;
        this.dbValue = dbValue;
    }

    public int getValue(){
        return value;
    }

}

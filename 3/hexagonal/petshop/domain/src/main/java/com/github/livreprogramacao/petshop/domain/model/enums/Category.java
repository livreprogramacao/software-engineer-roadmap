package com.github.livreprogramacao.petshop.domain.model.enums;

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

//@Schema(description = "Categoria do produto")
public enum Category {

    //@Schema(description = "Alimentação")
    FOOD(1, "FOOD"),

    //@Schema(description = "Brinquedos")
    TOY(2, "TOY"),

    //@Schema(description = "Acessórios")
    ACCESSORY(3, "ACCESSORY"),

    //@Schema(description = "Medicamentos")
    MEDICINE(4, "MEDICINE");

    private final int value;
    @Getter
    private final String label;

    Category(int value, String label){
        this.value = value;
        this.label = label;
    }

}

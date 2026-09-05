package com.petshop.company.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Categoria do produto")
public enum Category {

    @Schema(description = "Alimentação")
    FOOD(1, "FOOD"),

    @Schema(description = "Brinquedos")
    TOY(2, "TOY"),

    @Schema(description = "Acessórios")
    ACCESSORY(3, "ACCESSORY"),

    @Schema(description = "Medicamentos")
    MEDICINE(4, "MEDICINE");

    private final int value;
    @Getter
    private final String label;

    Category(int value, String label){
        this.value = value;
        this.label = label;
    }

    @JsonValue
    public String getLabel(){
        return label;
    }

    @JsonCreator
    public static Category fromValue(String value){
        for(Category type : values()){
            if(type.label.equals(value))
                return type;
        }
        throw new IllegalArgumentException("Invalid Category value " + value);
    }

}

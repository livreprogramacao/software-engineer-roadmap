package com.petshop.company.domain.dto;

import com.petshop.company.domain.model.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Dados do endereço")
public class CreateAddressRequest {

    @NotBlank
    @Schema(description = "Logradouro", example = "Rua das Flores")
    private String street;

    @NotBlank
    @Schema(description = "Número", example = "123")
    private String number;

    @NotBlank
    @Schema(description = "Cidade", example = "Rio de Janeiro")
    private String city;

    @NotBlank
    @Schema(description = "Estado", example = "RJ")
    private String state;

    @NotBlank
    @Schema(description = "CEP (8 dígitos)", example = "22041080")
    private String zipCode;

    @NotNull
    @Schema(description = "Tipo de endereço")
    private AddressType addressType;
}

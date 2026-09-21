package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.validator;

import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;
import com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound.VinValidator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseVinValidator implements VinValidator {

    @Override
    public boolean isValid(VehicleIdentificationNumber vehicleIdentificationNumber) {
        return true;
    }

}

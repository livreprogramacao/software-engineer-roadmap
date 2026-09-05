package com.github.livreprogramacao.registeringavehicle.domain.model.port.outbound;

import com.github.livreprogramacao.registeringavehicle.domain.model.VehicleIdentificationNumber;

public interface VinValidator {
    boolean isValid(VehicleIdentificationNumber vin);
}
